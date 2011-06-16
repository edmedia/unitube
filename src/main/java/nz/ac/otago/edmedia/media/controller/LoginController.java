package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.auth.util.AuthUtil;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Login Controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 4/07/2008
 *         Time: 14:23:49
 */
public class LoginController extends BaseFormController {

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
                    model.put("error", "Invalid token value. Please try again.");
                    // generate a new toake
                    String code = CommonUtil.generateRandomCode();
                    model.put(AuthUser.ONE_TIME_TOKEN_KEY, code);
                    session.setAttribute(AuthUser.ONE_TIME_TOKEN_KEY, code);
                    return getModelAndView(model, request, getFormView());
                } else {
                    if (AuthUtil.authenticate(user, userInDB, oneTimeToken)) {
                        // valid user
                        AuthUser authUser = AuthUtil.getAuthUser(userInDB, session);
                        AuthUtil.setAuthUser(session, authUser);
                        validUser = true;
                    }
                }
            }
        }
        if (!validUser) {
            logger.warn("Invalid user: username = " + user.getUserName() + " from " + request.getRemoteHost());
            Map model = new HashMap();
            model.put("error", "Invalid username or password. Please try again.");
            // generate a new token
            String code = CommonUtil.generateRandomCode();
            model.put(AuthUser.ONE_TIME_TOKEN_KEY, code);
            HttpSession session = request.getSession();
            session.setAttribute(AuthUser.ONE_TIME_TOKEN_KEY, code);
            return getModelAndView(model, request, getFormView());
        } else {
            String from = request.getParameter("from");
            if (StringUtils.isNotBlank(from)) {
                response.sendRedirect(from);
                return null;
            }
        }
        return getModelAndView(errors.getModel(), request);
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
        model.put("fromUrl", request.getParameter("from"));
        return model;
    }

    @Override
    protected ModelAndView showForm(HttpServletRequest request,
                                    HttpServletResponse response,
                                    BindException errors,
                                    Map controlModel)
            throws Exception {

        if (AuthUtil.isLoggedIn(request)) {
            logger.debug("Already logged in.");
            if (request.getParameter("from") != null) {
                response.sendRedirect(request.getParameter("from"));
                return null;
            } else
                return new ModelAndView(getSuccessView(), errors.getModel());
        } else
            return super.showForm(request, response, errors, controlModel);
    }
}

