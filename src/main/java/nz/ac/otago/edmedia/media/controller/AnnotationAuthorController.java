package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Annotation;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 26/11/2009
 *         Time: 14:08:13
 */
public class AnnotationAuthorController extends BaseOperationController {

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        Media media = null;
        Annotation annotation = null;

        Long mediaID = ServletUtil.getParameter(request, "i", 0L);
        if (mediaID > 0)
            // get media object
            media = (Media) service.get(Media.class, mediaID);
        Long annotID = ServletUtil.getParameter(request, "a", 0L);
        if (annotID > 0) {
            // get annotation object
            annotation = (Annotation) service.get(Annotation.class, annotID);
            media = annotation.getMedia();
        }
        Map model = errors.getModel();
        model.put("title", "Image Viewer: Edit Annotation");
        if (media != null) {
            model.put("obj", media);
            User user = MediaUtil.getCurrentUser(service, request);
            model.put("user", user);
            if (annotation != null)
                model.put("annotation", annotation);
        }
        return getModelAndView(model, request);
    }
}


