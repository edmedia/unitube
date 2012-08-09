package nz.ac.otago.edmedia.media.filter;

import nz.ac.otago.edmedia.auth.bean.AppInfo;
import nz.ac.otago.edmedia.auth.filter.AuthenticationFilter;
import nz.ac.otago.edmedia.auth.util.AuthUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter for SSO in ITS.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 8/8/2012
 *         Time: 10:57:05
 */
public class MediaFilter extends AuthenticationFilter {

    private static Logger log = LoggerFactory.getLogger(MediaFilter.class);

    private String ssoHeaderName;

    @Override
    public void init(FilterConfig config) throws ServletException {

        ssoHeaderName = config.getInitParameter("sso.header.name");
        AppInfo appInfo = new AppInfo();
        // if allowAccessWithoutLogin is true, user can access protected page, without login
        // when they do want to login (i.e. to be able to see more information), add parameter "login" with any value to the url
        appInfo.setAllowAccessWithoutLogin(Boolean.parseBoolean(config.getInitParameter(ALLOW_ACCESS_WITHOUT_LOGIN_INIT_PARAM)));
        appInfo.setUsingCAS(Boolean.parseBoolean(config.getInitParameter(USING_CAS_AUTHENTICATION_INIT_PARAM)));
        appInfo.setUsingEmbeddedAuthentication(Boolean.parseBoolean(config.getInitParameter(USING_EMBEDDED_AUTHENTICATION_INIT_PARAM)));
        if (appInfo.isUsingCAS()) {
            appInfo.setCasLogoutUrl(config.getInitParameter(LOGOUT_INIT_PARAM));
            appInfo.setLogoutUrl(config.getInitParameter(BB_LOGOUT_INIT_PARAM));
            appInfo.setCasCommunicationUrl(config.getInitParameter(COMMUNICATION_INIT_PARAM));
        }
        if (appInfo.isUsingEmbeddedAuthentication()) {
            appInfo.setEmbeddedLoginUrl(config.getInitParameter(EMBEDDED_LOGIN_URL_INIT_PARAM));
            if (StringUtils.isBlank(appInfo.getEmbeddedLoginUrl()))
                throw new ServletException("You are using embedded authentication, but didn't provide a login URL.");
        }
        appInfo.setInstructors(config.getInitParameter(INSTRUCTORS_INIT_PARAM));
        appInfo.setStudents(config.getInitParameter(STUDENTS_INIT_PARAM));
        appInfo.setCourses(config.getInitParameter(COURSES_INIT_PARAM));
        AuthUtil.setAppInfo(config.getServletContext(), appInfo);
        log.debug("appInfo = {}", appInfo);
        if (!appInfo.isUsingCAS() && !appInfo.isUsingEmbeddedAuthentication())
            throw new ServletException("No authentication method is set! You must set either CAS or Embedded Authentication.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String username = AuthUtil.getUserName(req);
        if (StringUtils.isBlank(username)) {
            // get username from HTTP header
            username = req.getHeader(ssoHeaderName);
            if (StringUtils.isNotBlank(username)) {
                log.debug("username is not set, set username to {} from HTTP header.", username);
                AuthUtil.setUserName(req.getSession(), username);
            } else {
                log.error("You are not logged in yet.");
                throw new ServletException("You are not logged in yet.");
            }
        }
        chain.doFilter(request, response);
    }

}
