package nz.ac.otago.edmedia.media.listener;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.bean.UploadLocation;
import nz.ac.otago.edmedia.spring.service.BaseService;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.File;
import java.util.List;

/**
 * When application init, set media's status from processing to waiting.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 17/03/2009
 *         Time: 16:44:47
 */
public class ApplicationInitListener implements ApplicationListener {

    private final Logger log = LoggerFactory.getLogger(ApplicationInitListener.class);

    private BaseService service;
    private UploadLocation uploadLocation;

    public void setService(BaseService service) {
        this.service = service;
    }

    public void setUploadLocation(UploadLocation uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ContextRefreshedEvent) {
            // where to store uploaded file?
            File uploadDir = uploadLocation.getUploadDir();
            File[] files = uploadDir.listFiles();
            if ((files != null) && (files.length > 0))
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    // ignore directory
                    if (file.isFile()) {
                        String accessCode = file.getName();
                        Media media = (Media) service.get(Media.class, CommonUtil.getId(accessCode));
                        if (media == null) {
                            MediaUtil.removeTmpFile(uploadLocation, accessCode);
                            log.info("remove tmp file: {}", accessCode);
                        } else if (media.getStatus() == MediaUtil.MEDIA_PROCESS_STATUS_PROCESSING) {
                            media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_WAITING);
                            service.update(media);
                            log.info("set to waiting(file system) (id={})", media.getId());
                        }
                    }
                }

            List list = service.list(Media.class);
            for (int i = 0; i < list.size(); i++) {
                Media media = (Media) list.get(i);
                if (media.getStatus() == MediaUtil.MEDIA_PROCESS_STATUS_PROCESSING) {
                    if (MediaUtil.createTmpFile(uploadLocation, media.getAccessCode())) {
                        media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_WAITING);
                        service.update(media);
                        log.info("set to waiting (id={})", media.getId());
                    }
                } else if (media.getStatus() == MediaUtil.MEDIA_PROCESS_STATUS_WAITING) {
                    MediaUtil.createTmpFile(uploadLocation, media.getAccessCode());
                }
            }
        }
    }
}
