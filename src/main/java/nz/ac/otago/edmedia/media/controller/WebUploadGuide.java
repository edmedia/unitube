package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.spring.controller.StaticContentController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Web upload guide.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 11/10/12
 *         Time: 9:42 AM
 */
public class WebUploadGuide extends StaticContentController {


    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,

                                                 HttpServletResponse response)
            throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("title", "Upload via the web");
        return getModelAndView(model, request);
    }

}
