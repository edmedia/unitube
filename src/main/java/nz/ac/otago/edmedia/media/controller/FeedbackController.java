package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.spring.controller.StaticContentController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Feedback Controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 10/11/2008
 *         Time: 11:31:23
 */
public class FeedbackController extends StaticContentController {

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("twitterUsername", username);
        model.put("title", "Tweet about UniTube");
        return getModelAndView(model, request);
    }

}
