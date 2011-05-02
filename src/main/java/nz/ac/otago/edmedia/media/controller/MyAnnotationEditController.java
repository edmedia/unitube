package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Annotation;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Annotation edit controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 26/11/2009
 *         Time: 10:40:42
 */
public class MyAnnotationEditController extends BaseFormController {

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {

        Annotation annotation = (Annotation) command;

        Map model = errors.getModel();
        return getModelAndView(model, request);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map model = super.referenceData(request);
        if (model == null)
            model = new HashMap();
        User user = MediaUtil.getCurrentUser(service, request);
        model.put("user", user);
        return model;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        // get object from parent class
        Object obj = super.formBackingObject(request);
        Annotation annotation = (Annotation) obj;
        User user = MediaUtil.getCurrentUser(service, request);
        if ((annotation == null) || (annotation.getAuthor() == null) || (user == null))
            throw new ServletException("Your session is expired.");
        if (!annotation.getAuthor().getUserName().equals(user.getUserName()))
            throw new ServletException("You can not edit other's media file.");
        return obj;
    }

}

