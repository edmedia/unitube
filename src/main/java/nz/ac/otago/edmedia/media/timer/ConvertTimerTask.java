package nz.ac.otago.edmedia.media.timer;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.converter.MediaConverter;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.bean.UploadLocation;
import nz.ac.otago.edmedia.spring.service.BaseService;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.io.File;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Timer Task to convert uploaded video to H.264 video.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 29/02/2008
 *         Time: 11:38:37
 */
public class ConvertTimerTask extends TimerTask {

    private final static Logger log = LoggerFactory.getLogger(ConvertTimerTask.class);

    private final static int DEFAULT_MAXIMUM_THREAD_NUMBER = 5;

    private final static int DEFATUL_MAXIMUM_PROCESS_TIMES = 9;

    private static boolean isRunning = false;

    protected BaseService service;

    private UploadLocation uploadLocation;

    private MediaConverter mediaConverter;

    private int maxThreadNumber;

    private int maxProcessTimes;

    public void setService(BaseService service) {
        this.service = service;
    }

    public void setUploadLocation(UploadLocation uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    public void setMediaConverter(MediaConverter mediaConverter) {
        this.mediaConverter = mediaConverter;
    }

    public void setMaxThreadNumber(int maxThreadNumber) {
        if (maxThreadNumber > 0)
            this.maxThreadNumber = maxThreadNumber;
        else
            this.maxThreadNumber = DEFAULT_MAXIMUM_THREAD_NUMBER;
    }

    public void setMaxProcessTimes(int maxProcessTimes) {
        if (maxProcessTimes > 0)
            this.maxProcessTimes = maxProcessTimes;
        else
            this.maxProcessTimes = DEFATUL_MAXIMUM_PROCESS_TIMES;
    }

    private ExecutorService executor;

    /**
     * Run timer task.
     */
    public void run() {
        if (isRunning) {
            log.info("ConvertTimerTask is already running.");
        } else {
            isRunning = true;
            log.info("ConvertTimerTask is running.");
            try {
                if (executor == null) {
                    log.info("executor = Executors.newFixedThreadPoll({})", maxThreadNumber);
                    executor = Executors.newFixedThreadPool(maxThreadNumber);
                }
                // where to store uploaded file?
                File uploadDir = uploadLocation.getUploadDir();
                File[] files = null;
                if (uploadDir != null)
                    files = uploadDir.listFiles();
                if (files != null) {
                    for (File file : files)
                        // only deal with files, not directories
                        if (file.exists() && file.isFile()) {
                            // only deal with files without extension
                            if (StringUtils.isBlank(FilenameUtils.getExtension(file.getAbsolutePath()))) {
                                String accessCode = file.getName();
                                Media media = (Media) service.get(Media.class, CommonUtil.getId(accessCode));
                                // if media doesn't exist, or has finished or unrecognized, remove tmp file
                                if ((media == null) ||
                                        (!media.getUser().getIsGuest() && ((media.getStatus() == MediaUtil.MEDIA_PROCESS_STATUS_FINISHED) ||
                                                (media.getStatus() == MediaUtil.MEDIA_PROCESS_STATUS_UNRECOGNIZED)))
                                        ) {
                                    MediaUtil.removeTmpFile(uploadLocation, accessCode);
                                    log.info("remove tmp file: accessCode = {}, id = {}", accessCode, CommonUtil.getId(accessCode));
                                } else if (media.getUser().getIsGuest() &&
                                        ((media.getStatus() == MediaUtil.MEDIA_PROCESS_STATUS_FINISHED) ||
                                                (media.getStatus() == MediaUtil.MEDIA_PROCESS_STATUS_UNRECOGNIZED))
                                        ) {
                                    if (MediaUtil.removeMediaAfter24FromGuest(uploadLocation, media, service))
                                        log.info("remove guest media file after 24 hours.");
                                } else if (media.getStatus() == MediaUtil.MEDIA_PROCESS_STATUS_PROCESSING) {
                                    log.info("media file (accessCode = {}, id = {}) is already in process.", accessCode, CommonUtil.getId(accessCode));
                                } else
                                    processMedia(media);
                            }
                        }
                }

                // find all media which need to convert to another format, and do only the first
                // criteria:
                // 1. convert finished 2. video file only 3. otherFormatFilename is null 4. convertTo is not null
                SearchCriteria criteria = new SearchCriteria.Builder()
                        .eq("status", MediaUtil.MEDIA_PROCESS_STATUS_FINISHED)
                        .eq("mediaType", MediaUtil.MEDIA_TYPE_VIDEO)
                        .isNull("otherFormatFilename")
                        .isNotNull("convertTo")
                        .build();
                @SuppressWarnings("unchecked")
                List<Media> list = (List<Media>) service.search(Media.class, criteria);
                // only convert one each time
                if (!list.isEmpty()) {
                    Media media = list.get(0);
                    if (StringUtils.isNotBlank(media.getConvertTo()))
                        convertToOtherFormat(media);
                }
            } catch (Exception e) {
                log.error("Exception when converting new media file.", e);
            } finally {
                isRunning = false;
                log.info("ConvertTimerTask is not running.");
            }
        }
    }

    /**
     * Process media file
     *
     * @param media media
     */
    private void processMedia(Media media) {
        log.info("Processing media {} {}", media.getAccessCode(), media.getId());
        // process all waiting media files
        if (media.getStatus() == MediaUtil.MEDIA_PROCESS_STATUS_WAITING) {
            if (media.getProcessTimes() < maxProcessTimes) {
                try {
                    // set status to processing
                    media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_PROCESSING);
                    media.setProcessTimes(media.getProcessTimes() + 1);
                    // update database
                    service.update(media);
                    // put it into running queue
                    log.info("Put media {} into running queue", media.getId());
                    executor.execute(new MediaTask(service, uploadLocation, mediaConverter, media));
                } catch (DataAccessException e) {
                    log.error("Can not set status to " + media.getStatus(), e);
                }
            } else {
                // set status to unrecognized
                media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_UNRECOGNIZED);
                try {
                    // update database
                    service.update(media);
                } catch (DataAccessException e) {
                    log.error("Can not set status to " + media.getStatus(), e);
                }
            }
        }
    }

    /**
     * Convert video file to other format
     *
     * @param media media
     */
    private void convertToOtherFormat(Media media) {
        File mediaDir = MediaUtil.getMediaDirectory(uploadLocation, media);
        File file = new File(mediaDir, media.getUploadFileUserName());
        try {
            String filename = mediaConverter.toOtherFormat(file, file.getParentFile(), media.getConvertTo());
            // load media from database again
            // why???
            // because it may be changed during conversion
            media = (Media) service.get(Media.class, media.getId());
            File otherFile = new File(mediaDir, filename);
            if (otherFile.length() > 0) {
                media.setOtherFormatFilename(filename);
                service.update(media);
                MediaUtil.removeTmpFile(uploadLocation, media.getAccessCode());
            }
        } catch (DataAccessException e) {
            log.error("Can not convert to other format", e);
        }
    }

}

