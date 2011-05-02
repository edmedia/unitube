package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.spring.controller.StaticContentController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout Controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 16/06/2008
 *         Time: 11:51:37
 */
public class LogoutController extends StaticContentController {

    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        session.invalidate();
        return super.handleRequestInternal(request, response);
    }

}
