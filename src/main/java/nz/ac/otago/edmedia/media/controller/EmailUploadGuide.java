package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.spring.controller.StaticContentController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Email upload guide.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 11/10/12
 *         Time: 9:39 AM
 */
public class EmailUploadGuide extends StaticContentController {


    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,

                                                 HttpServletResponse response)
            throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("title", "Upload via email");
        return getModelAndView(model, request);
    }

}

