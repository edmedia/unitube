package nz.ac.otago.edmedia.media.interceptor;

import nz.ac.otago.edmedia.auth.bean.AppInfo;
import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.auth.util.AuthUtil;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.service.BaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Authentication & Authorization Interceptor.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 17/09/2009
 *         Time: 12:25:59
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final static Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    private BaseService service;

    public void setService(BaseService service) {
        this.service = service;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession();
        String username = AuthUtil.getUserName(request);
        if (StringUtils.isBlank(username)) {
            username = request.getRemoteUser();
            if (StringUtils.isNotBlank(username)) {
                log.debug("username is not set, set username to {}.", username);
                AuthUtil.setUserName(session, username);
            }
        }
        // if logged in already
        if (StringUtils.isNotBlank(username)) {
            AuthUser authUser = MediaUtil.getAuthUser(session);
            if ((authUser == null)) {
                ServletContext context = session.getServletContext();
                AppInfo appInfo = AuthUtil.getAppInfo(context);
                if ((appInfo != null) && appInfo.isUsingCAS()) {
                    // if CAS communication url is not empty, get user info from CAS
                    if (StringUtils.isNotBlank(appInfo.getCasCommunicationUrl()))
                        authUser = MediaUtil.getUserInfo(appInfo, username);
                    if (authUser == null) {
                        String ldapUrl = context.getInitParameter("ldapUrl");
                        String baseDN = context.getInitParameter("baseDN");
                        String ldapPrincipal = context.getInitParameter("ldapPrincipal");
                        String ldapCredentials = context.getInitParameter("ldapCredentials");
                        authUser = MediaUtil.getUserInfoFromLDAP(username, ldapUrl, baseDN, ldapPrincipal, ldapCredentials);
                        authUser = MediaUtil.alterAuthUser(authUser, appInfo);
                    }
                    if (authUser != null) {
                        log.debug("AuthUser is not set, set AuthUser to {}.", authUser);
                        AuthUtil.setAuthUser(session, authUser);
                    } else {
                        // we have problem to get authUser from LDAP
                        User user = MediaUtil.getUser(service, username, null);
                        if (user != null) {
                            authUser = AuthUtil.getAuthUser(user, session);
                            if (authUser != null) {
                                log.debug("AuthUser is not set, set AuthUser to {}.", authUser);
                                AuthUtil.setAuthUser(session, authUser);
                            }
                        }
                    }
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

}
