package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.spring.util.ModelUtil;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @SuppressWarnings("unchecked")
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {

        Map model = new HashMap();
        model.put("twitterUsername", username);
        return getModelAndView(model, request);
    }

}
