package nz.ac.otago.edmedia.media.timer;

import nz.ac.otago.edmedia.media.bean.*;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Timer task to check email for new uploaded media file
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 15/04/2010
 *         Time: 9:47:08 AM
 */
public class CheckEmailTimerTask extends BaseTimerTask {

    private final static Logger log = LoggerFactory.getLogger(CheckEmailTimerTask.class);

    private static boolean isRunning = false;

    private String consumerKey;

    private String consumerSecret;

    private String accessToken;

    private String accessTokenSecret;

    private String proxyHost;

    private int proxyPort;

    private String proxyUser;

    private String proxyPassword;

    private String emailProtocol;

    private String emailServer;

    private String emailUsername;

    private String emailPassword;

    private String antivirus;

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public void setEmailProtocol(String emailProtocol) {
        this.emailProtocol = emailProtocol;
    }

    public void setEmailServer(String emailServer) {
        this.emailServer = emailServer;
    }

    public void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public void setAntivirus(String antivirus) {
        this.antivirus = antivirus;
    }

    /**
     * Run timer task.
     */
    public void run() {
        if (isRunning) {
            log.warn("CheckEmailTimerTask is already running.");
            return;
        }
        boolean outputLog = outputLog();
        try {
            isRunning = true;
            if (outputLog)
                log.info("CheckEmailTimerTask is running.");
            // check email posting
            if (StringUtils.isNotBlank(emailProtocol)
                    && StringUtils.isNotBlank(emailServer)
                    && StringUtils.isNotBlank(emailUsername)
                    && StringUtils.isNotBlank(emailPassword))
                checkEmailPosting();

        } catch (Exception e) {
            log.error("Exception when checking email.", e);
        } finally {
            isRunning = false;
            if (outputLog)
                log.info("CheckEmailTimerTask is not running.");
        }
    }

    /**
     * Check email posting
     */
    private void checkEmailPosting() {
        Store store = null;
        Folder folder = null;
        try {
            // -- Get hold of the default session --
            Properties props = System.getProperties();
            javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, null);
            // -- Get hold of a message store, and connect to it --
            store = session.getStore(emailProtocol);
            store.connect(emailServer, emailUsername, emailPassword);
            // -- Try to get hold of the default folder --
            folder = store.getDefaultFolder();
            if (folder == null) {
                log.error("No default folder");
                return;
            }
            // -- ...and its INBOX --
            folder = folder.getFolder("INBOX");
            if (folder == null) {
                log.error("No INBOX folder");
                return;
            }
            // -- Open the folder for read only --
            folder.open(Folder.READ_WRITE);
            // -- Get the message wrappers and process them --
            Message[] msgs = folder.getMessages();
            for (Message msg : msgs) {
                boolean viaMMS = false;
                User user = findUserByEmailAddress(msg);
                if (user == null) {
                    user = findUserByMobileNumber(msg);
                    if (user != null)
                        viaMMS = true;
                }
                if (user != null) {
                    handleMessage(msg, user, viaMMS);
                    // only deal with one email once
                    break;
                }
            }
        } catch (NoSuchProviderException e) {
            log.error("Protocol \"" + emailProtocol + "\" is not supported.", e);
        } catch (MessagingException e) {
            log.error("Messaging exception", e);
        } catch (IOException e) {
            log.error("IO exception", e);
        } finally {
            // -- Close down nicely --
            try {
                // expunge the deleted messages
                if (folder != null) folder.close(true);
                if (store != null) store.close();
            } catch (MessagingException e) {
                log.error("Messaging exception", e);
            }
        }
    }

    public void handleMessage(Message message, User user, boolean viaMMS)
            throws MessagingException, IOException {
        handleMessage(message, user, viaMMS, null);

    }

    public void handleMessage(Message message, User user, boolean viaMMS, Album album)
            throws MessagingException, IOException {
        // get from address
        String from = ((InternetAddress) message.getFrom()[0]).getPersonal();
        if (from == null)
            from = ((InternetAddress) message.getFrom()[0]).getAddress();
        else
            from = "[" + from + "] " + ((InternetAddress) message.getFrom()[0]).getAddress();
        String subject = message.getSubject();
        String content = null;
        // if email is plain text or html
        if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
            content = getEmailContent(message.getInputStream());
        }

        // The content of your message is a Multipart object when it has attachments.
        if (message.getContent() instanceof Multipart) {
            Multipart mp = (Multipart) message.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                Part part = mp.getBodyPart(i);
                String disposition = part.getDisposition();
                if (disposition == null) {
                    MimeBodyPart mbp = (MimeBodyPart) part;
                    if (mbp.isMimeType("text/plain") || mbp.isMimeType("text/html")) {
                        content = getEmailContent(mbp.getInputStream());
                    } else {
                        // log information here
                        log.warn("content type is not text/plain or text/html. content type is " + mbp.getContentType());

                        // deal with attachment
                        if (mbp.isMimeType("image/jpeg")) {
                            String filename = "upload.jpg";
                            if (mbp.getFileName() != null)
                                filename = mbp.getFileName();
                            handleAttachment(filename, mbp, user, message, viaMMS, album, subject, content);
                        }
                    }
                } else if (disposition.equalsIgnoreCase(Part.ATTACHMENT) ||
                        disposition.equalsIgnoreCase(Part.INLINE)) {
                    // deal with attachment
                    String filename = part.getFileName();
                    handleAttachment(filename, part, user, message, viaMMS, album, subject, content);
                }
            }
        }
        log.debug("FROM: " + from);
        log.debug("SUBJECT: " + subject);
        log.debug("SENT TIME: " + message.getSentDate());
        log.debug("RECEIVE TIME: " + message.getReceivedDate());
        log.debug("CONTENT: " + content);
    }

    /**
     * Find out which user sent this message
     *
     * @param message email message
     * @return user who sent this message, null if not found.
     */
    private User findUserByEmailAddress(Message message) {
        User user = null;
        try {
            // try to find user from "from" address
            if (message.getFrom() != null) {
                // go through all "from" addresses
                for (Address add : message.getFrom()) {
                    InternetAddress iAdd = (InternetAddress) add;
                    String from = iAdd.getAddress();
                    List list = service.search(User.class, "email", from);
                    if (!list.isEmpty())
                        user = (User) list.get(0);

                }
            }
        } catch (MessagingException e) {
            log.error("message exception", e);
        }
        return user;
    }

    /**
     * Find out which user sent this message
     *
     * @param message email message
     * @return user who sent this message, null if not found.
     */
    private User findUserByMobileNumber(Message message) {
        User user = null;
        try {
            // try to find user from "from" address
            if (message.getFrom() != null) {
                // go through all "from" addresses
                for (Address add : message.getFrom()) {
                    InternetAddress iAdd = (InternetAddress) add;
                    String from = iAdd.getAddress();
                    String name = from.substring(0, from.indexOf("@"));
                    // if all numbers
                    if (StringUtils.isNumeric(name)) {
                        String num = name.substring(2); // first two numbers are country code
                        SearchCriteria criteria = new SearchCriteria.Builder()
                                .like("mobile", "%" + num)
                                .build();
                        List lst = service.search(User.class, criteria);
                        if ((lst != null) && (lst.size() > 0))
                            user = (User) lst.get(0);
                    }
                }
            }
        } catch (MessagingException e) {
            log.error("message exception", e);
        }
        return user;
    }

    private String getEmailContent(InputStream input) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(input));
            String thisLine = reader.readLine();
            while (thisLine != null) {
                content.append(thisLine);
                content.append("\n");
                thisLine = reader.readLine();
            }
        } catch (IOException e) {
            log.error("IOException", e);
        }
        return content.toString();

    }

    private void handleAttachment(String filename,
                                  Part part, User user,
                                  Message message,
                                  boolean viaMMS,
                                  Album album,
                                  String title,
                                  String description)
            throws MessagingException, IOException {
        // deal with attachment

        // change extension of filename to lower case
        boolean hasExtension = !"".equals(FilenameUtils.getExtension(filename));
        if (hasExtension)
            filename = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename).toLowerCase();

        log.debug("filename = " + filename);
        Media media = new Media();
        media.setUser(user);
        // if album is not null
        // we are dealing with Mobile learning project
        // set media to hidden
        if (album != null) {
            media.setAccessType(MediaUtil.MEDIA_ACCESS_TYPE_HIDDEN);
            media.setTags(StringUtils.substringBefore(album.getAlbumName(), "."));
        } else if (user.getIsGuest()) {
            // set access type to private for guest
            media.setAccessType(MediaUtil.MEDIA_ACCESS_TYPE_HIDDEN);
        } else {
            // use user default access type
            media.setAccessType(user.getEmailUploadAccessType());
        }
        // if title is not null, set to title
        if (title != null)
            media.setTitle(title);
        else if (hasExtension) // otherwise, set title to filename, without extension
            media.setTitle(FilenameUtils.getBaseName(filename));
        else
            media.setTitle(filename);
        if (description != null)
            media.setDescription(description);

        // set upload time to when we receive the email
        media.setUploadTime(message.getReceivedDate());
        String randomCode = CommonUtil.generateRandomCode();
        media.setRandomCode(randomCode);
        // for private file, set a different locationCode
        if (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PRIVATE)
            media.setLocationCode(CommonUtil.generateRandomCode());
        else
            media.setLocationCode(randomCode);

        File mediaDir = MediaUtil.getMediaDirectory(uploadLocation, media);
        // Save attachment in email to right place
        filename = saveFile(mediaDir, filename, part.getInputStream());
        media.setUploadFileUserName(filename);

        // virus scan uploaded file
        Map<String, Object> result = MediaUtil.virusScan(antivirus, media, uploadLocation);
        // if has virus inside
        if (!result.get("status").equals(0)) {
            log.warn("Found virus in file " + media.getUploadFileUserName());
            MediaUtil.removeMediaFiles(uploadLocation, media, true);
            // delete this email
            message.setFlag(Flags.Flag.DELETED, true);
            return;
        }

        media.setIsOnOtherServer(false);
        // we don't convert it at this time, leave it to TimerTask
        media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_WAITING);

        media.setViaEmail(true);
        media.setViaMMS(viaMMS);

        log.debug("random code for this media is " + randomCode);
        try {
            service.save(media);
            if (album != null) {
                // add this media to this album
                AlbumMedia albumMedia = new AlbumMedia();
                albumMedia.setMedia(media);
                albumMedia.setAlbum(album);
                service.save(albumMedia);
                // if this user is not allowed to access this album yet,
                // add it
                SearchCriteria criteria = new SearchCriteria.Builder()
                        .eq("user", user)
                        .eq("album", album)
                        .build();
                List list = service.search(UserAlbum.class, criteria);
                if (list.isEmpty()) {
                    UserAlbum userAlbum = new UserAlbum();
                    userAlbum.setUser(user);
                    userAlbum.setAlbum(album);
                    service.save(userAlbum);
                }
            }
            MediaUtil.createTmpFile(uploadLocation, media.getAccessCode());
            StringBuilder url = new StringBuilder(appURL);
            if (!url.toString().endsWith("/"))
                url.append("/");
            url.append("view?m=");
            url.append(media.getAccessCode());
            MessageSourceAccessor msa = new MessageSourceAccessor(ctx);
            String subject = msa.getMessage("upload.email.unitube.subject.via.email", new String[]{filename, user.getFirstName()});
            if (viaMMS)
                subject = msa.getMessage("upload.email.unitube.subject.via.mms", new String[]{filename, user.getFirstName()});
            // have a tweet on UniTube Twitter if media is public
            if (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC) {
                MediaUtil.updateTwitter(consumerKey, consumerSecret, accessToken, accessTokenSecret,
                        proxyHost, proxyPort, proxyUser, proxyPassword,
                        subject + " " + url);
            }
            // send an email to user
            String youSubject = msa.getMessage("upload.email.subject.via.email", new String[]{filename});
            if (viaMMS)
                youSubject = msa.getMessage("upload.email.subject.via.email", new String[]{filename});

            String youBody = msa.getMessage("upload.email.body", new String[]{url.toString()});
            OtherUtil.sendEmail(mailHost, fromEmail, smtpUsername, smtpPassword, smtpPort,
                    user.getEmail(), youSubject, youBody);
            // send an email to unitube email address
            String body = msa.getMessage("upload.email.unitube.body", new String[]{url.toString()});
            OtherUtil.sendEmail(mailHost, fromEmail, smtpUsername, smtpPassword, smtpPort,
                    fromEmail, subject, body);
            // delete this email
            message.setFlag(Flags.Flag.DELETED, true);
        } catch (DataAccessException e) {
            log.error("DataAccessException", e);
        }
    }

    private String saveFile(File dir, String filename, InputStream input) {
        // if filename has non-alphanumeric character, replace it with "_"
        char[] newNameArray = new char[filename.length()];
        filename.getChars(0, filename.length(), newNameArray, 0);
        for (int i = 0; i < filename.length(); i++) {
            // ignore '.' and '-'
            if ((filename.charAt(i) != '.') && (filename.charAt(i) != '-')) {
                if (!CharUtils.isAsciiAlphanumeric(filename.charAt(i)))
                    newNameArray[i] = '_';
            }
        }
        filename = new String(newNameArray);
        OutputStream output = null;
        try {
            output = new FileOutputStream(new File(dir, filename));
            IOUtils.copy(input, output);
        } catch (FileNotFoundException e) {
            log.error("File not found: " + filename);
        } catch (IOException e) {
            log.error("IO Exception: ", e);
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(input);
        }
        return filename;
    }

}


