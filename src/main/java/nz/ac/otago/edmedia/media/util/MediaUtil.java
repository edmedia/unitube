package nz.ac.otago.edmedia.media.util;

import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.auth.util.AuthUtil;
import nz.ac.otago.edmedia.media.bean.*;
import nz.ac.otago.edmedia.spring.bean.UploadLocation;
import nz.ac.otago.edmedia.spring.service.BaseService;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.spring.util.UploadUtil;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Utitlity class for media.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 23/01/2008
 *         Time: 16:34:30
 */
public class MediaUtil {

    private final static Logger log = LoggerFactory.getLogger(MediaUtil.class);

    // 0: waiting, 1: processing, 2:finished
    public static final int MEDIA_PROCESS_STATUS_WAITING = 0;
    public static final int MEDIA_PROCESS_STATUS_PROCESSING = 1;
    public static final int MEDIA_PROCESS_STATUS_FINISHED = 2;
    public static final int MEDIA_PROCESS_STATUS_UNRECOGNIZED = 9;

    public static final int MEDIA_TYPE_UNKNOWN = 0;
    public static final int MEDIA_TYPE_OTHER_MEDIA = 1;
    public static final int MEDIA_TYPE_IMAGE = 5;
    public static final int MEDIA_TYPE_AUDIO = 10;
    public static final int MEDIA_TYPE_VIDEO = 20;

    public static final int MEDIA_ACCESS_TYPE_PUBLIC = 0;
    public static final int MEDIA_ACCESS_TYPE_HIDDEN = 10;
    public static final int MEDIA_ACCESS_TYPE_PRIVATE = 20;

    /**
     * Returns user for given userName and wayf
     *
     * @param service  service
     * @param userName userName
     * @param wayf     where are you from
     * @return user for given userName and wayf
     */
    @SuppressWarnings("unchecked")
    public static User getUser(BaseService service, String userName, String wayf) {
        User user = null;
        List<User> list = null;
        if (userName != null) {
            SearchCriteria.Builder builder = new SearchCriteria.Builder();
            builder = builder.eq("userName", userName);
            if (wayf != null)
                builder = builder.eq("wayf", wayf);
            SearchCriteria criteria = builder.build();
            list = (List<User>) service.search(User.class, criteria);
        }
        if ((list != null) && !list.isEmpty())
            user = list.get(0);
        return user;
    }

    /**
     * Returns current login user.
     *
     * @param service  service
     * @param authUser authUser object
     * @return current login user
     */
    public static User getCurrentUser(BaseService service, AuthUser authUser) {
        User user = null;
        if (authUser != null) {
            // get user from database
            user = getUser(service, authUser.getUserName(), authUser.getWayf());
            if (user == null) {
                // if not in database, create a new one
                user = new User();
                user.setUserName(authUser.getUserName());
                // default password is the same as username
                user.setPassWord(DigestUtils.md5Hex(user.getUserName()));
                user.setWayf(authUser.getWayf());
                user.setFirstName(authUser.getFirstName());
                user.setLastName(authUser.getLastName());
                user.setEmail(authUser.getEmail());
                user.setRandomCode(CommonUtil.generateRandomCode());
                service.save(user);
            }
        }
        return user;
    }

    /**
     * Returns current login user.
     *
     * @param service service
     * @param request request object
     * @return current login user
     * @throws ServletException if user has not logged in yet
     */
    public static User getCurrentUser(BaseService service, HttpServletRequest request)
            throws ServletException {
        AuthUser authUser = AuthUtil.getAuthUser(request);
        return getCurrentUser(service, authUser);
    }

    /**
     * Returns unique media directory for given media and locationCode. The unique directory is something like
     * ${ROOT}/owner_of_media_accessCode/locationCode/.
     *
     * @param uploadLocation uploadLocation
     * @param media          media
     * @param locationCode   locationCode
     * @param createDir      create dir?
     * @return unique media directory
     */
    private static File getMediaDirectory(UploadLocation uploadLocation, Media media, String locationCode, boolean createDir) {
        File personalDir = uploadLocation.getUploadDir();
        personalDir = new File(uploadLocation.getUploadDir(), media.getUser().getAccessCode());
        File mediaDir = new File(personalDir, locationCode);
        // create media directory if createDir is true, and directory does not exist
        if (createDir && !mediaDir.exists())
            if (!mediaDir.mkdirs())
                log.warn("Can't create media directory \"{}\"", mediaDir);
        return mediaDir;
    }

    /**
     * Returns unique media directory for given media and locationCode. The unique directory is something like
     * ${ROOT}/owner_of_media_accessCode/locationCode/.
     *
     * @param uploadLocation uploadLocation
     * @param media          media
     * @param locationCode   locationCode
     * @return unique media directory
     */
    private static File getMediaDirectory(UploadLocation uploadLocation, Media media, String locationCode) {
        return getMediaDirectory(uploadLocation, media, locationCode, true);
    }

    /**
     * Returns unique media directory for given media and user. The unique directory is something like
     * ${ROOT}/user_accessCode/media_locationCode/,
     * or ${ROOT}/user_accessCode/media_randomCode/ if locationCode is empty
     *
     * @param uploadLocation uploadLocation
     * @param media          media
     * @param user           user
     * @param createDir      create dir?
     * @return unique media directory
     */
    public static File getMediaDirectory(UploadLocation uploadLocation, Media media, User user, boolean createDir) {
        File personalDir = uploadLocation.getUploadDir();
        // if user is not null, add user_accessCode to path
        if ((user != null) && StringUtils.isNotBlank(user.getAccessCode()))
            personalDir = new File(uploadLocation.getUploadDir(), user.getAccessCode());
        String location = media.getLocationCode();
        if (StringUtils.isBlank(location))
            location = media.getRandomCode();
        File mediaDir = new File(personalDir, location);
        // create media directory if createDir is true, and directory does not exist
        if (createDir && !mediaDir.exists())
            if (!mediaDir.mkdirs())
                log.warn("Can't create media directory \"{}\"", mediaDir);
        return mediaDir;
    }

    /**
     * Returns unique media directory for given media and user. The unique directory is something like
     * ${ROOT}/user_accessCode/media_locationCode/,
     * or ${ROOT}/user_accessCode/media_randomCode/ if locationCode is empty
     *
     * @param uploadLocation uploadLocation
     * @param media          media
     * @param user           user
     * @return unique media directory
     */
    public static File getMediaDirectory(UploadLocation uploadLocation, Media media, User user) {
        return getMediaDirectory(uploadLocation, media, user, true);
    }

    /**
     * Returns unique media directory for given media. The unique directory is something like
     * ${ROOT}/user_accessCode/media_locationCode/,
     * or ${ROOT}/user_accessCode/media_randomCode/ if locationCode is empty
     *
     * @param uploadLocation uploadLocation
     * @param media          media
     * @param createDir      create dir?
     * @return unique media directory
     */
    public static File getMediaDirectory(UploadLocation uploadLocation, Media media, boolean createDir) {
        return getMediaDirectory(uploadLocation, media, media.getUser(), createDir);
    }

    /**
     * Returns unique media directory for given media. The unique directory is something like
     * ${ROOT}/user_accessCode/media_locationCode/,
     * or ${ROOT}/user_accessCode/media_randomCode/ if locationCode is empty
     *
     * @param uploadLocation uploadLocation
     * @param media          media
     * @return unique media directory
     */
    public static File getMediaDirectory(UploadLocation uploadLocation, Media media) {
        return getMediaDirectory(uploadLocation, media, media.getUser());
    }

    /**
     * Returns unique annotation directory for given annotation file. The unique directory is something like
     * ${ROOT}/user_accessCode/annotation_randomCod/
     *
     * @param uploadLocation uploadLocation
     * @param annot          annotation
     * @return unique annotation directory
     */
    public static File getAnnotationDirectory(UploadLocation uploadLocation, Annotation annot) {
        // personal directory
        File personalDir = new File(uploadLocation.getUploadDir(), annot.getAuthor().getAccessCode());
        File annotationDir = new File(personalDir, annot.getRandomCode());
        // create annotation directory if does not exist
        if (!annotationDir.exists())
            if (!annotationDir.mkdirs())
                log.warn("Can't create annotation directory \"{}\"", annotationDir);
        return annotationDir;
    }

    /**
     * Removes files for given media object.
     *
     * @param uploadLocation     uploadLocation
     * @param media              media
     * @param removeUploadedFile remove user uploaded file?
     */
    public static void removeMediaFiles(UploadLocation uploadLocation, Media media, boolean removeUploadedFile) {
        // get media directory
        File mediaDir = MediaUtil.getMediaDirectory(uploadLocation, media);
        if (mediaDir.exists()) {
            if (removeUploadedFile) {
                // delete media direcotry totally
                if (!FileUtils.deleteQuietly(mediaDir))
                    log.warn("Can't delete media directory \"{}\"", mediaDir);
            } else {
                // delete all files under media dir, exception user uploaded file
                File[] files = mediaDir.listFiles();
                for (File file : files)
                    // if this file is not user uploaded file
                    if (!file.getAbsolutePath().endsWith(media.getUploadFileUserName()))
                        if (!FileUtils.deleteQuietly(file))
                            log.warn("Can't delete file \"{}\"", file);
            }
        }
    }

    /**
     * Removes files for given annotation object.
     *
     * @param uploadLocation uploadLocation
     * @param annotation     annotation
     */
    public static void removeAnnotationFiles(UploadLocation uploadLocation, Annotation annotation) {
        // get annotation directory
        File annotationDir = MediaUtil.getAnnotationDirectory(uploadLocation, annotation);
        if (annotationDir.exists()) {
            // delete annotation direcotry totally
            if (!FileUtils.deleteQuietly(annotationDir))
                log.warn("Can't delete annotation directory \"{}\"", annotationDir);
        }
    }

    public static void saveUploaedFile(UploadLocation uploadLocation, Media media) {
        String originalFilename = media.getUploadFile().getOriginalFilename();
        // get media directory
        File mediaDir = MediaUtil.getMediaDirectory(uploadLocation, media);
        // save uploaded file in media object to mediaDir
        UploadUtil.saveUploadFile(media, mediaDir);

        // if filename has non-alphanumeric character, replace it with "_"
        char[] newNameArray = new char[originalFilename.length()];
        originalFilename.getChars(0, originalFilename.length(), newNameArray, 0);
        for (int i = 0; i < originalFilename.length(); i++) {
            // ignore '.' and '-'
            if ((originalFilename.charAt(i) != '.') && (originalFilename.charAt(i) != '-')) {
                if (!CharUtils.isAsciiAlphanumeric(originalFilename.charAt(i)))
                    newNameArray[i] = '_';
            }
        }
        String newName = new String(newNameArray);
        // change extension of filename to lower case
        boolean hasExtension = !"".equals(FilenameUtils.getExtension(newName));
        if (hasExtension)
            newName = FilenameUtils.getBaseName(newName) + "." + FilenameUtils.getExtension(newName).toLowerCase();
        // if new name is different from old name, rename this file
        if (!newName.equals(originalFilename)) {
            File movieFile = new File(mediaDir, originalFilename);
            boolean renameSuccess = movieFile.renameTo(new File(mediaDir, newName));
            if (renameSuccess)
                media.setUploadFileUserName(newName);
        }
    }

    public static void saveUploaedFile(UploadLocation uploadLocation, Annotation annotation) {
        String originalFilename = annotation.getAnnotFile().getOriginalFilename();
        // get annotation directory
        File annotationDir = MediaUtil.getAnnotationDirectory(uploadLocation, annotation);
        UploadUtil.saveUploadFile(annotation, annotationDir);

        // if filename has non-alphanumeric character, replace it with "_"
        char[] newNameArray = new char[originalFilename.length()];
        originalFilename.getChars(0, originalFilename.length(), newNameArray, 0);
        for (int i = 0; i < originalFilename.length(); i++) {
            // ignore ".", "-"
            if ((originalFilename.charAt(i) != '.') && (originalFilename.charAt(i) != '-')) {
                if (!CharUtils.isAsciiAlphanumeric(originalFilename.charAt(i)))
                    newNameArray[i] = '_';
            }
        }
        String newName = new String(newNameArray);
        // change extension of filename to lower case
        if (!"".equals(FilenameUtils.getExtension(newName)))
            newName = FilenameUtils.getBaseName(newName) + "." + FilenameUtils.getExtension(newName).toLowerCase();
        // if new name is different from old name, rename this file
        if (!newName.equals(originalFilename)) {
            File movieFile = new File(annotationDir, originalFilename);
            boolean renameSuccess = movieFile.renameTo(new File(annotationDir, newName));
            if (renameSuccess)
                annotation.setAnnotFileUserName(newName);
        }
    }

    /**
     * Create a tmp file named accessCode under upload directory
     *
     * @param uploadLocation uploadLocation
     * @param accessCode     accessCode
     * @return returns true only if successful
     */
    public static boolean createTmpFile(UploadLocation uploadLocation, String accessCode) {
        boolean result = false;
        File file = new File(uploadLocation.getUploadDir(), accessCode);
        try {
            if (!file.exists())
                result = file.createNewFile();
        } catch (IOException e) {
            log.warn("Can not create tmp file \"{}\"", file);
        }
        return result;
    }

    /**
     * Remove the tmp file named accessCode under upload directory
     *
     * @param uploadLocation uploadLocation
     * @param accessCode     accessCode
     * @return returns true only if successful
     */
    public static boolean removeTmpFile(UploadLocation uploadLocation, String accessCode) {
        boolean result = false;
        File file = new File(uploadLocation.getUploadDir(), accessCode);
        if (file.exists()) {
            result = file.delete();
            if (!result)
                log.warn("Can not delete tmp file \"{}\"", file);
        }
        return result;
    }

    public static boolean removeMediaAfter24FromGuest(UploadLocation uploadLocation, Media media, BaseService service) {
        boolean result = false;
        // if media file is from guest user, check it
        if ((media != null) && media.getUser().getIsGuest()) {
            Date now = new Date();
            // if it's older than 24 hours, delete it
            if ((now.getTime() - media.getUploadTime().getTime()) > (24 * 60 * 60 * 1000)) {
                log.info("delete this media(id={}, accessCode={}) and all files after 24 hours", media.getId(), media.getAccessCode());
                MediaUtil.removeMediaFiles(uploadLocation, media, true);
                try {
                    service.delete(media);
                    result = true;
                } catch (DataAccessException e) {
                    log.error("Can't delete this media(id=" + media.getId() + ", accessCode=" + media.getAccessCode() + ")", e);
                }
            } else {
                // if it's public, hide it. All media files from guest users are hidden.
                if (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC) {
                    media.setAccessType(MediaUtil.MEDIA_ACCESS_TYPE_HIDDEN);
                    try {
                        service.update(media);
                    } catch (DataAccessException e) {
                        log.error("Can not set access type to hidden", e);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Update twitter status.
     *
     * @param consumerKey       consumer key
     * @param consumerSecret    consumer secret
     * @param accessToken       access token
     * @param accessTokenSecret access token secret
     * @param status            status
     * @return true if successful, false otherwise
     */
    public static boolean updateTwitter(String consumerKey,
                                        String consumerSecret,
                                        String accessToken,
                                        String accessTokenSecret,
                                        String status) {
        boolean success = false;
        if (StringUtils.isNotBlank(consumerKey) &&
                StringUtils.isNotBlank(consumerSecret) &&
                StringUtils.isNotBlank(accessToken) &&
                StringUtils.isNotBlank(accessTokenSecret) &&
                StringUtils.isNotBlank(status)) {
            AccessToken token = new AccessToken(accessToken, accessTokenSecret);
            Twitter twitter = new TwitterFactory().getInstance();
            //Twitter twitter = new TwitterFactory().getInstance(token);
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            twitter.setOAuthAccessToken(token);
            //Twitter twitter = new TwitterFactory().getOAuthAuthorizedInstance(consumerKey, consumerSecret, token);
            try {
                twitter.updateStatus(status);
                success = true;
            } catch (TwitterException e) {
                log.error("exception when updating twitter", e);
            }
        } else {
            log.warn("updateTwitter: required parameters are empty.");
        }
        return success;
    }

    /**
     * Search media files
     *
     * @param words     keyword
     * @param session   session
     * @param mediaType media type
     * @return a list of all media files
     */
    public static List searchMedia(String words, Session session, int mediaType) {
        Class searchClass = Media.class;
        String[] fields = new String[]{"title", "description", "tags"};
        Criteria criteria = session.createCriteria(searchClass);
        criteria.add(Restrictions.eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC));
        criteria.add(Restrictions.eq("status", MediaUtil.MEDIA_PROCESS_STATUS_FINISHED));
        if (mediaType != MediaUtil.MEDIA_TYPE_UNKNOWN)
            criteria.add(Restrictions.eq("mediaType", mediaType));
        criteria.addOrder(Property.forName("uploadTime").desc());
        log.debug("search media for \"" + words + "\"");
        return luceneSearch(session, criteria, fields, words, searchClass);
    }

    /**
     * Search albums.
     *
     * @param words   keyword
     * @param session session
     * @return a list of all albums
     */
    public static List searchAlbum(String words, Session session) {
        Class searchClass = Album.class;
        String[] fields = new String[]{"albumName", "description"};
        Criteria criteria = session.createCriteria(searchClass);
        criteria.add(Restrictions.eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC));
        criteria.addOrder(Property.forName("albumName").asc());
        log.debug("search album for \"" + words + "\"");
        return luceneSearch(session, criteria, fields, words, searchClass);
    }

    /**
     * Search users.
     *
     * @param words   keyword
     * @param session session
     * @return a list of all users
     */
    public static List searchUser(String words, Session session) {
        Class searchClass = User.class;
        String[] fields = new String[]{"userName", "firstName", "lastName"};
        Criteria criteria = session.createCriteria(searchClass);
        criteria.add(Restrictions.eq("isGuest", Boolean.FALSE));
        criteria.add(Restrictions.eq("disabled", Boolean.FALSE));
        criteria.addOrder(Property.forName("lastLoginTime").desc());
        log.debug("search user for \"" + words + "\"");
        return luceneSearch(session, criteria, fields, words, searchClass);
    }

    private static List luceneSearch(Session session, Criteria criteria, String[] fields, String words, Class searchClass) {
        List result = null;
        // create and execute a search
        FullTextSession fullTextSession = Search.createFullTextSession(session);
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer());
        try {
            Query query = parser.parse(words);
            // wrap Lucene query in a org.hibernate.Query
            FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, searchClass);
            fullTextQuery.setCriteriaQuery(criteria);
            result = fullTextQuery.list();
        } catch (ParseException e) {
            log.error("Parse exception", e);
        }
        return result;
    }

    public static IVOption getIVOption(Media media, BaseService service) {
        IVOption ivOption = null;
        List list = service.search(IVOption.class, "media", media);
        if (!list.isEmpty())
            ivOption = (IVOption) list.get(0);
        return ivOption;
    }

    /**
     * Returns how many seconds for give duration string, like 00:02:03.55 hh:MM:ss.mm
     *
     * @param duration duration string
     * @return how many seconds
     */
    public static int howManySeconds(String duration) {
        return (howManyMilliseconds(duration) / 1000);
    }

    /**
     * Returns how many milliseconds for give duration string, like 00:02:03.55 hh:MM:ss.mm
     *
     * @param duration duration string
     * @return how many milliseconds
     */
    public static int howManyMilliseconds(String duration) {
        int sss = 0;
        if (duration != null) {
            String sh = StringUtils.substringBefore(duration, ":");
            String sm = StringUtils.substringBetween(duration, ":", ":");
            String ss = StringUtils.substringBetween(duration, ":", ".");
            String sms = StringUtils.substringAfter(duration, ".");
            if ((ss != null) && (StringUtils.contains(ss, ":")))
                ss = StringUtils.substringAfter(ss, ":");
            int h = 0, m = 0, s = 0, ms = 0;
            try {
                if (sh != null)
                    h = Integer.parseInt(sh.trim());
                if (sm != null)
                    m = Integer.parseInt(sm.trim());
                if (ss != null)
                    s = Integer.parseInt(ss.trim());
                if (sms != null)
                    ms = Integer.parseInt(sms.trim());
            } catch (NumberFormatException e) {
                log.warn("Unexpected time format {}.", duration);
            }
            sss = ((h * 60 + m) * 60 + s) * 1000 + ms * 10;
        }
        return sss;
    }

    /**
     * Returns how many pages for give duration string, like 12
     *
     * @param duration duration string
     * @return how many pages
     */
    public static int howManyPages(String duration) {
        int sss = 0;
        if (duration != null) {
            try {
                sss = Integer.parseInt(duration);
            } catch (NumberFormatException e) {
                log.warn("Unexpected page number format {}.", duration);
            }
        }
        return sss;
    }

    /**
     * Move media file to a new owner.
     *
     * @param media          media
     * @param newOwner       new owner
     * @param uploadLocation uploadLocation
     * @return true if successful
     */
    public static boolean moveMediaFile(Media media, User newOwner, UploadLocation uploadLocation) {
        boolean result = false;
        if ((media != null) && (newOwner != null) && (uploadLocation != null)) {
            // get new location for given media file
            File newLocation = getMediaDirectory(uploadLocation, media, newOwner);
            File oldLocation = getMediaDirectory(uploadLocation, media, false);
            // if newLocation exists, delete it
            if (newLocation.exists())
                if (!newLocation.delete())
                    log.warn("Has problem to delete {}", newLocation.getAbsolutePath());
            try {
                FileUtils.moveDirectory(oldLocation, newLocation);
                result = true;
            } catch (IOException ioe) {
                log.error("Can't move media files.", ioe);
            }
        }
        return result;
    }

    /**
     * Move media file to a new location.
     *
     * @param media          media
     * @param uploadLocation uploadLocation
     * @return true if successful
     */
    public static boolean moveMediaFile(Media media, String newLocationCode, UploadLocation uploadLocation) {
        boolean result = false;
        if ((media != null) && (uploadLocation != null)) {
            // get new location for given media file
            File newLocation = getMediaDirectory(uploadLocation, media, newLocationCode);
            File oldLocation = getMediaDirectory(uploadLocation, media, false);
            // if newLocation exists, delete it
            if (newLocation.exists())
                if (!newLocation.delete())
                    log.warn("Has problem to delete {}", newLocation.getAbsolutePath());
            try {
                FileUtils.moveDirectory(oldLocation, newLocation);
                result = true;
            } catch (IOException ioe) {
                log.error("Can't move media files.", ioe);
            }
        }
        return result;
    }

    public static boolean canView(Media media, User user) {
        boolean result = false;
        if ((media != null) && (user != null)) {
            // if current user is the owner of the media file
            if (user.getId().equals(media.getUser().getId()))
                result = true;
            else {
                // go through accessRule list
                for (AccessRule accessRule : media.getAccessRules()) {
                    if (accessRule.getUser() != null) {
                        if (accessRule.getUser().getId().equals(user.getId())) {
                            result = true;
                            break;
                        }
                    } else {
                        if (accessRule.getUserInput().contains(user.getEmail())
                                || accessRule.getUserInput().contains(user.getUserName())) {
                            result = true;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }


    public static void main(String args[]) {
    }

}
