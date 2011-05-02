package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.auth.util.AuthUtil;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Authentication Controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 22/10/2008
 *         Time: 09:44:07
 */
public class AuthenticationController extends BaseFormController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {

        boolean validUser = false;
        User user = (User) command;
        // use this if we want everyone can login
        //User userInDB = MediaUtil.getUser(service, user.getUserName(), null);
        //only registered user can login (for registered user, wayf is AuthUser.EMBEDDED_WAYF
        User userInDB = MediaUtil.getUser(service, user.getUserName(), AuthUser.EMBEDDED_WAYF);
        if (userInDB != null) {
            String oneTimeToken = request.getParameter(AuthUser.ONE_TIME_TOKEN_KEY);
            if (oneTimeToken != null) {
                HttpSession session = request.getSession();
                String tokenInSession = (String) session.getAttribute(AuthUser.ONE_TIME_TOKEN_KEY);
                if (!oneTimeToken.equals(tokenInSession)) {
                    logger.warn("Invalid token: username = " + user.getUserName() + " from " + request.getRemoteHost());
                    Map model = new HashMap();
                    model.put("error", "Invalid token");
                    return getModelAndView(model, request);
                }
            }
            if (AuthUtil.authenticate(user, userInDB, oneTimeToken)) {
                // valid user
                HttpSession session = request.getSession();
                AuthUser authUser = AuthUtil.getAuthUser(userInDB, session);
                AuthUtil.setAuthUser(session, authUser);
                validUser = true;
            }
        }
        if (!validUser) {
            logger.warn("Invalid user: username = " + user.getUserName() + " from " + request.getRemoteHost());
            Map model = new HashMap();
            model.put("error", "Invalid user");
            String code = CommonUtil.generateRandomCode();
            model.put(AuthUser.ONE_TIME_TOKEN_KEY, code);
            HttpSession session = request.getSession();
            session.setAttribute(AuthUser.ONE_TIME_TOKEN_KEY, code);
            return getModelAndView(model, request);
        }
        Map model = new HashMap();
        return getModelAndView(model, request);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map model = super.referenceData(request);
        if (model == null)
            model = new HashMap();
        String code = CommonUtil.generateRandomCode();
        model.put(AuthUser.ONE_TIME_TOKEN_KEY, code);
        HttpSession session = request.getSession();
        session.setAttribute(AuthUser.ONE_TIME_TOKEN_KEY, code);
        return model;
    }


}
