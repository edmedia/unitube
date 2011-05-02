package nz.ac.otago.edmedia.media.filter;

import nz.ac.otago.edmedia.auth.bean.AppInfo;
import nz.ac.otago.edmedia.auth.filter.AuthenticationFilter;
import nz.ac.otago.edmedia.auth.util.AuthUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Filter for UniTube.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 1/12/2009
 *         Time: 10:57:05
 */
public class MediaFilter extends AuthenticationFilter {

    private static Logger logger = LoggerFactory.getLogger(MediaFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String reqUrl = req.getRequestURI();
        String query = req.getQueryString();
        if (StringUtils.isNotBlank(query))
            reqUrl += "?" + query;
        if ((reqUrl.contains("/myTube/") || reqUrl.contains("/admin/")) && (req.getParameter("login") == null)) {
            if (!AuthUtil.isLoggedIn(req)) {
                AppInfo appInfo = AuthUtil.getAppInfo(req.getSession().getServletContext());
                String embeddedLoginUrl = appInfo.getEmbeddedLoginUrl();
                // redirect to login page
                String url = req.getContextPath();
                if (!embeddedLoginUrl.startsWith("/"))
                    url += "/";
                url += embeddedLoginUrl;
                if (url.contains("?"))
                    url += "&from=" + URLEncoder.encode(reqUrl, "UTF-8");
                else
                    url += "?from=" + URLEncoder.encode(reqUrl, "UTF-8");
                logger.info("Redirect to login page: " + url);
                res.sendRedirect(url);
                return;
            }
        }
        super.doFilter(request, response, chain);
    }

}
