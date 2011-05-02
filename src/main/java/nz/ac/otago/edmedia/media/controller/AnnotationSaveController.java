package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Annotation;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 26/11/2009
 *         Time: 14:03:01
 */
public class AnnotationSaveController extends BaseOperationController {

    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        Annotation annotation = (Annotation) command;
        // get id from access code
        long userID = CommonUtil.getId(request.getParameter("userID"));
        long imageID = CommonUtil.getId(request.getParameter("imageID"));
        User user = null;
        if (userID > 0)
            user = (User) service.get(User.class, userID);
        Media media = null;
        if (imageID > 0)
            media = (Media) service.get(Media.class, imageID);
        response.setContentType("text/plain");
        if ((user != null) && (media != null)) {
            annotation.setMedia(media);
            annotation.setAuthor(user);
            annotation.setAnnotTime(new Date());
            String randomCode = CommonUtil.generateRandomCode();
            annotation.setRandomCode(randomCode);
            logger.debug("random code for this annotation is " + randomCode);
            MediaUtil.saveUploaedFile(getUploadLocation(), annotation);
            service.save(annotation);
            logger.debug("access code for this annotation is " + annotation.getAccessCode());
            response.getWriter().write("Annotation saved.");
        } else
            response.getWriter().write("We have problem to save your annotation, please try again or contact us to report this problem.");
        return null;
    }
}
