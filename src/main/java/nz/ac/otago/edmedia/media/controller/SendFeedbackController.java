package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Sends feeback to twitter.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 22/07/2008
 *         Time: 17:00:35
 */
public class SendFeedbackController extends AbstractController {

    private String consumerKey;

    private String consumerSecret;

    private String accessToken;

    private String accessTokenSecret;

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String status = request.getParameter("status");
        boolean success = MediaUtil.updateTwitter(consumerKey, consumerSecret, accessToken, accessTokenSecret, status);
        OtherUtil.responseXml(response, "feedback", success);
        return null;
    }

}
