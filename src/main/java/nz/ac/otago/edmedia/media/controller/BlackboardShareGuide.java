package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.spring.controller.StaticContentController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Blackboard share guide.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 11/10/12
 *         Time: 9:35 AM
 */
public class BlackboardShareGuide extends StaticContentController {


    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,

                                                 HttpServletResponse response)
            throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("title", "Share via BlackBoard");
        return getModelAndView(model, request);
    }

}
