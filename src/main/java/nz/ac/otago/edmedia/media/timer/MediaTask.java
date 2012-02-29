package nz.ac.otago.edmedia.media.timer;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.MediaInfo;
import nz.ac.otago.edmedia.media.converter.MediaConverter;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.bean.UploadLocation;
import nz.ac.otago.edmedia.spring.service.BaseService;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;

import java.io.File;

/**
 * Deal with media format conversion.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 16/07/2008
 *         Time: 09:52:48
 */
class MediaTask implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(MediaTask.class);

    // service
    protected BaseService service;

    private UploadLocation uploadLocation;

    private MediaConverter mediaConverter;

    private Media media;

    private String mailHost;

    private String fromEmail;

    private String smtpUsername;

    private String smtpPassword;

    private int smtpPort;

    private String appURL;

    private ApplicationContext ctx;

    public MediaTask(BaseService service, UploadLocation uploadLocation, MediaConverter mediaConverter, Media media,
                     String mailHost, String fromEmail, String smtpUsername, String smtpPassword, int smtpPort, String appURL, ApplicationContext ctx) {
        this.service = service;
        this.uploadLocation = uploadLocation;
        this.mediaConverter = mediaConverter;
        this.media = media;
        this.mailHost = mailHost;
        this.fromEmail = fromEmail;
        this.smtpUsername = smtpUsername;
        this.smtpPassword = smtpPassword;
        this.smtpPort = smtpPort;
        this.appURL = appURL;
        this.ctx = ctx;
    }

    public void run() {
        // assume conversion failed
        boolean conversionFailed = true;
        Long id = media.getId();
        if ((service != null) && (uploadLocation != null) && (mediaConverter != null) &&
                (media != null) && (id != null)) {
            // reload media object
            media = (Media) service.get(Media.class, id);
            File mediaDir = null;
            MediaInfo mediaInfo = null;
            if (media != null) {
                // remove old files
                MediaUtil.removeMediaFiles(uploadLocation, media, false);
                File file = null;
                mediaDir = MediaUtil.getMediaDirectory(uploadLocation, media);
                if (mediaDir != null)
                    file = new File(mediaDir, media.getUploadFileUserName());
                try {
                    if ((file != null) && file.exists()) {
                        StopWatch sw = new StopWatch();
                        sw.start();
                        // do conversion
                        mediaInfo = mediaConverter.transcode(file, file.getParentFile());
                        sw.stop();
                        log.info("File [{}] [m={}] took [{}] to convert.", new Object[]{file.getAbsolutePath(), media.getAccessCode(), sw});
                    }
                } catch (Exception e) {
                    log.error("Exception when converting media file", e);
                }
            }
            // after conversion
            // reload media object
            // why???
            // because it may be changed during conversion
            media = (Media) service.get(Media.class, id);
            // conversion is successful only when mediaInfo is not null and filename is not null
            if ((media != null) && (mediaInfo != null) && (mediaInfo.getFilename() != null)) {
                File newFile = new File(mediaDir, mediaInfo.getFilename());
                // if output file exists and not empty
                if (newFile.exists() && (newFile.length() > 0)) {
                    String filename = mediaInfo.getFilename();
                    media.setRealFilename(filename);
                    // set media type according mediaInfo object
                    if (mediaInfo.getVideo() != null) {
                        media.setMediaType(MediaUtil.MEDIA_TYPE_VIDEO);
                        // set video duration
                        media.setDuration(MediaUtil.howManyMilliseconds(mediaInfo.getDuration()));
                    } else if (mediaInfo.getAudio() != null) {
                        media.setMediaType(MediaUtil.MEDIA_TYPE_AUDIO);
                        // set audio duration
                        media.setDuration(MediaUtil.howManyMilliseconds(mediaInfo.getDuration()));
                    } else if (mediaInfo.getImage() != null)
                        media.setMediaType(MediaUtil.MEDIA_TYPE_IMAGE);
                    else if (mediaInfo.getImages() != null) {
                        media.setMediaType(MediaUtil.MEDIA_TYPE_OTHER_MEDIA);
                        media.setDuration(MediaUtil.howManyPages(mediaInfo.getDuration()));
                    } else
                        media.setMediaType(MediaUtil.MEDIA_TYPE_OTHER_MEDIA);
                    if (mediaInfo.getThumbnail() != null)
                        media.setThumbnail(mediaInfo.getThumbnail());
                    media.setWidth(mediaInfo.getWidth());
                    media.setHeight(mediaInfo.getHeight());
                    try {
                        media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_FINISHED);
                        // update database
                        service.update(media);

                        // send an email to owner after conversion is finished
                        MessageSourceAccessor msa = new MessageSourceAccessor(ctx);
                        StringBuilder url = new StringBuilder(appURL);
                        if (!url.toString().endsWith("/"))
                            url.append("/");
                        url.append("view?m=");
                        url.append(media.getAccessCode());
                        String subject = msa.getMessage("conversion.finished.email.subject");
                        String body = msa.getMessage("conversion.finished.email.body", new String[]{url.toString()});
                        OtherUtil.sendEmail(mailHost, fromEmail, smtpUsername, smtpPassword, smtpPort,
                                media.getUser().getEmail(), subject, body);

                        MediaUtil.recordUploadOrUpdateAfterConversion(service, media);

                        // only remove tmp file for normal user
                        // keep tmp file for guest user, so we can check if 24 hours past
                        if (!media.getUser().getIsGuest())
                            MediaUtil.removeTmpFile(uploadLocation, media.getAccessCode());
                        conversionFailed = false;
                    } catch (DataAccessException e) {
                        log.error("Can not update database for media " + media.getId(), e);
                    }
                }
            }
        }
        if (conversionFailed) {
            // reload media object
            media = (Media) service.get(Media.class, id);
            if (media != null) {
                // if conversion failed, set status back to waiting
                media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_WAITING);
                try {
                    // update database
                    service.update(media);
                } catch (DataAccessException e) {
                    log.error("Can not update database for media " + media.getId(), e);
                }
            }
        }
    }

}
