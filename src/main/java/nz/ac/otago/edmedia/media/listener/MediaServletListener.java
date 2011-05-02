package nz.ac.otago.edmedia.media.listener;

import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.auth.util.AuthUtil;
import nz.ac.otago.edmedia.listener.BaseServletListener;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.service.BaseService;
import nz.ac.otago.edmedia.spring.util.BeanUtil;
import org.springframework.web.context.WebApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet Listener.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 26/11/2008
 *         Time: 15:32:16
 */
public class MediaServletListener extends BaseServletListener {

    private final Logger log = LoggerFactory.getLogger(MediaServletListener.class);

    public static final String SERVICE_KEY = "myBaseService";

    public static final String LOGIN_TIME_KEY = "loginTime";

    /**
     * When user logs in.
     *
     * @param session session
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void userLogin(HttpSession session) {
        AuthUser authUser = AuthUtil.getAuthUser(session);
        if (authUser != null) {
            // for login user, timeout is 30 minutes
            session.setMaxInactiveInterval(1800);
            ServletContext ctx = session.getServletContext();
            WebApplicationContext webCtx = BeanUtil.getWebApplicationContext(ctx);
            if ((webCtx != null) && webCtx.containsBean(SERVICE_KEY)) {
                Date now = new Date();
                // get service bean
                BaseService service = (BaseService) webCtx.getBean(SERVICE_KEY);
                User user = MediaUtil.getCurrentUser(service, authUser);
                // if first login time is null, set it to now
                if (user.getFirstLoginTime() == null)
                    user.setFirstLoginTime(now);
                user.setLoginTimes(user.getLoginTimes() + 1);
                service.update(user);
                // set loginTime to session
                session.setAttribute(LOGIN_TIME_KEY, now);
                // store current user to map
                synchronized (this) {
                    Map map = (Map) ctx.getAttribute(LOGIN_USER_MAP_KEY);
                    if (map == null) {
                        map = new HashMap();
                    }
                    map.put(session.getId(), user);
                    ctx.setAttribute(LOGIN_USER_MAP_KEY, map);
                }
                String ipAddress = getIPAddress(session.getId());
                if(ipAddress ==null)
                    log.info("{} login @ {} ({})", new Object[]{user.getUserName(), now, authUser});
                else
                    log.info("{} login @ {} from {} ({})", new Object[]{user.getUserName(), now, ipAddress, authUser});
            }
        }
    }

    /**
     * When user logs out.
     *
     * @param session session
     */
    @Override
    protected void userLogout(HttpSession session) {
        AuthUser authUser = AuthUtil.getAuthUser(session);
        if (authUser != null) {
            ServletContext ctx = session.getServletContext();
            WebApplicationContext webCtx = BeanUtil.getWebApplicationContext(ctx);
            if ((webCtx != null) && webCtx.containsBean(SERVICE_KEY)) {
                BaseService service = (BaseService) webCtx.getBean(SERVICE_KEY);
                User user = MediaUtil.getCurrentUser(service, authUser);
                if (user != null) {
                    // get loginTime from session
                    Date loginTime = (Date) session.getAttribute(LOGIN_TIME_KEY);
                    // if loginTime is not available, we use session.getCreationTime as loginTime
                    if (loginTime == null)
                        loginTime = new Date(session.getCreationTime());
                    Date now = new Date();
                    long lasts = now.getTime() - loginTime.getTime();
                    String ipAddress = getIPAddress(session.getId());
                    if (ipAddress == null)
                        log.info("{} logout @ {} lasts for {} ({})", new Object[]{user.getUserName(), now, lasts, authUser});
                    else
                        log.info("{} logout @ {} from {} lasts for {} ms ({})", new Object[]{user.getUserName(), now, ipAddress, lasts, authUser});
                    // update user information to database
                    // load user from database
                    // why? because user may already change their information
                    User userInDB = (User) service.get(User.class, user.getId());
                    // if user is still in database, update it
                    if (userInDB != null) {
                        userInDB.setOnlineTime(user.getOnlineTime() + lasts);
                        userInDB.setLastLoginIP(ipAddress);
                        userInDB.setLastLoginTime(loginTime);
                        service.update(userInDB);
                    }
                    // remove current user from map
                    synchronized (this) {
                        Map map = (Map) ctx.getAttribute(LOGIN_USER_MAP_KEY);
                        if (map != null) {
                            // remove this user from map
                            map.remove(session.getId());
                            // store updated map to ServletContext
                            ctx.setAttribute(LOGIN_USER_MAP_KEY, map);
                        }
                    }
                }
            }
        }
    }

}
