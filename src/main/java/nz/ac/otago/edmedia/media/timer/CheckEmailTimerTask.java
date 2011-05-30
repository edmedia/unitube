package nz.ac.otago.edmedia.media.timer;

import nz.ac.otago.edmedia.media.bean.*;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.bean.UploadLocation;
import nz.ac.otago.edmedia.spring.service.BaseService;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

/**
 * Timer task to check email for new uploaded media file
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 15/04/2010
 *         Time: 9:47:08 AM
 */
public class CheckEmailTimerTask
        extends TimerTask
        implements ApplicationContextAware {

    private final static Logger log = LoggerFactory.getLogger(CheckEmailTimerTask.class);

    private static boolean isRunning = false;

    // service
    protected BaseService service;

    private UploadLocation uploadLocation;

    private String consumerKey;

    private String consumerSecret;

    private String accessToken;

    private String accessTokenSecret;

    private String mailHost;

    private String fromEmail;

    private String smtpUsername;

    private String smtpPassword;

    private int smtpPort;

    private String emailProtocol;

    private String emailServer;

    private String emailUsername;

    private String emailPassword;

    private String appURL;

    private ApplicationContext ctx;

    public BaseService getService() {
        return service;
    }

    public void setService(BaseService service) {
        this.service = service;
    }

    public void setUploadLocation(UploadLocation uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

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

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
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

    public void setAppURL(String appURL) {
        this.appURL = appURL;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ctx = applicationContext;
    }

    /**
     * Run timer task.
     */
    public void run() {

        if (isRunning) {
            log.info("CheckEmailTimerTask is already running.");
        } else {
            isRunning = true;
            log.info("CheckEmailTimerTask is running.");
            try {
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
                log.info("CheckEmailTimerTask is not running.");
            }
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
        // MediaUtil.saveUploaedFile(getUploadLocation(), media);
        // Save attchment in email to right place
        saveFile(mediaDir, filename, part.getInputStream());
        media.setUploadFileUserName(filename);

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
            //MessageSourceAccessor msa = new MessageSourceAccessor(ctx);
            String subject = user.getFirstName() + " just uploaded " + filename + " to UniTube via Email";
            if (viaMMS)
                subject = user.getFirstName() + " just uploaded " + filename + " to UniTube via Mobile";
            // have a tweet on UniTube Twitter if media is public
            if (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC) {
                MediaUtil.updateTwitter(consumerKey, consumerSecret, accessToken, accessTokenSecret, subject + " " + url);
            }
            // send an email to user
            String youSubject = "You just uploaded " + filename + " to UniTube via Email";
            if (viaMMS)
                youSubject = "You just uploaded " + filename + " to UniTube via Mobile";

            String youBody = "Here is the link to your media file: " + url;
            OtherUtil.sendEmail(mailHost, fromEmail, smtpUsername, smtpPassword, smtpPort,
                    user.getEmail(), youSubject, youBody);
            // send an email to unitube email address
            String body = "Here is the link to the media file: " + url;
            subject = user.getFirstName() + " just uploaded " + filename + " to UniTube";
            OtherUtil.sendEmail(mailHost, fromEmail, smtpUsername, smtpPassword, smtpPort,
                    fromEmail, subject, body);
            // delete this email
            message.setFlag(Flags.Flag.DELETED, true);
        } catch (DataAccessException e) {
            log.error("DataAccessException", e);
        }
    }

    private void saveFile(File dir, String filename, InputStream input) {
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
    }

}


