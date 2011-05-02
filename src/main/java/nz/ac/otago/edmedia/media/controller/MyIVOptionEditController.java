package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.IVOption;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ImageViewer Option edit controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 6/01/2010
 *         Time: 09:37:42
 */
public class MyIVOptionEditController extends BaseFormController {

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {
        try {
            service.update(command);
        } catch (DataAccessException e) {
            logger.error(e);
        }
        IVOption iVOption = (IVOption) command;
        // return to media edit page
        String viewName = getSuccessView();
        viewName = viewName + "?id=" + iVOption.getMediaID();
        return new ModelAndView(viewName);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        // get object from parent class
        Object obj = super.formBackingObject(request);
        IVOption ivOption = (IVOption) obj;
        User user = MediaUtil.getCurrentUser(service, request);
        if (ivOption == null)
            throw new ServletException("Can't find this ImageViewer option.");
        else if ((ivOption.getMedia().getUser() == null) || (user == null))
            throw new ServletException("Your session is expired.");
        if (!ivOption.getMedia().getUser().getId().equals(user.getId()))
            throw new ServletException("You can not edit other's ImageViewer option.");
        return obj;
    }

}
