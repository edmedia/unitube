package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.spring.controller.StaticContentController;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Admin controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 6/03/13
 *         Time: 1:25 PM
 */
public class AdminController extends StaticContentController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response)
            throws Exception {

         StringBuilder url = new StringBuilder(ServletUtil.getContextURL(request));
        if (!url.toString().endsWith("/"))
            url.append("/");
        url.append("admin/adminUserList.do");
        logger.info("redirect to " + url);
        response.sendRedirect(url.toString());
        return null;
    }

}
