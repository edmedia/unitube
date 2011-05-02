package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.auth.util.AuthUtil;
import nz.ac.otago.edmedia.spring.controller.StaticContentController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * We will delete this controller sooner.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 3/06/2008
 *         Time: 11:08:47
 */
public class GuestController extends StaticContentController {

    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        AuthUser authUser = AuthUtil.getGuestUser();
        AuthUtil.setAuthUser(session, authUser);
        return super.handleRequestInternal(request, response);
    }

}
