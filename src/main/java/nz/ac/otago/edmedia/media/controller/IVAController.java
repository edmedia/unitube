package nz.ac.otago.edmedia.media.controller;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Parameters to ImageViewer applet.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 9/12/2009
 *         Time: 15:14:24
 */
public class IVAController extends ImageViewerController {

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        // the same as ImagerViewer controller, except content type is javascript
        response.setContentType("text/javascript");
        response.setCharacterEncoding("UTF-8");
        return super.handle(request, response, command, errors);
    }
}


