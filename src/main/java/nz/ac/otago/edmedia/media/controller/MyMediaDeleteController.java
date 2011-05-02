package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Annotation;
import nz.ac.otago.edmedia.media.bean.IVOption;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.bean.IDListBean;
import nz.ac.otago.edmedia.spring.controller.BaseDeleteController;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Delete a media object from database.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 4/02/2008
 *         Time: 10:28:54
 */
public class MyMediaDeleteController extends BaseDeleteController {

    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        if (command != null) {
            // get user info
            User user = MediaUtil.getCurrentUser(service, request);

            IDListBean idList = (IDListBean) command;
            if (idList.getId() != null) {
                for (int i = 0; i < idList.getId().length; i++) {
                    Media media = (Media) service.get(getBeanClass(), idList.getId()[i]);
                    // only owner can delete their own media
                    if ((media != null) && media.getUser().getId().equals(user.getId())) {
                        // delete ivOption for image file
                        if (media.getMediaType() == MediaUtil.MEDIA_TYPE_IMAGE) {
                            IVOption ivOption = MediaUtil.getIVOption(media, service);
                            // delete ivOption if exists
                            if (ivOption != null)
                                service.delete(ivOption);
                        }
                        for (Annotation annotation : media.getAnnotations())
                            // remove annotation files
                            MediaUtil.removeAnnotationFiles(getUploadLocation(), annotation);
                        // delete uploaded file, converted file and thumbnail
                        MediaUtil.removeMediaFiles(getUploadLocation(), media, true);
                        service.delete(media);
                    }
                }
            }
        }
        return getModelAndView(errors.getModel(), request);
    }

}
