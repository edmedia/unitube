package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Annotation;
import nz.ac.otago.edmedia.media.bean.IVOption;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * ImageViewer controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 13/11/2009
 *         Time: 11:51:44
 */
public class ImageViewerController extends BaseOperationController {

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        Media media = null;
        Annotation annotation = null;

        // get access code from url
        String i = request.getParameter("i");
        if (StringUtils.isNotBlank(i)) {
            // get id from access code
            long id = CommonUtil.getId(i);
            if (id > 0)
                // get media object
                media = (Media) service.get(Media.class, id);
            if ((media != null) && !media.validCode(i))
                media = null;
        }

        String a = request.getParameter("a");
        if (StringUtils.isNotBlank(a)) {
            // deal with annotation
            long id = CommonUtil.getId(a);
            if (id > 0)
                annotation = (Annotation) service.get(Annotation.class, id);
            if ((annotation != null) && !annotation.validCode(a))
                annotation = null;
            if (annotation != null)
                media = annotation.getMedia();
        }
        Map model = errors.getModel();
        model.put("title", "Image Viewer");
        if (media != null) {
            model.put("obj", media);
            IVOption ivOption = MediaUtil.getIVOption(media, service);
            if (ivOption != null)
                model.put("ivOption", ivOption);
            User user = MediaUtil.getCurrentUser(service, request);
            if ((user != null) && user.getId().equals(media.getUser().getId()))
                model.put("isOwner", Boolean.TRUE);
            if (annotation != null)
                model.put("annotation", annotation);
        }
        return getModelAndView(model, request);
    }
}

