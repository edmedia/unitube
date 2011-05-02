package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Player controller, which generates the embeded code for video.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 25/06/2008
 *         Time: 09:27:16
 */
public class PlayerController extends BaseOperationController {

    private static boolean AUTO_PLAY_DEFAULT = false;
    private static boolean AUTO_BUFFERING_DEFAULT = false;

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        // get access code from url
        String m = request.getParameter("m");
        // get id from access code
        long id = CommonUtil.getId(m);
        Media media = null;
        if (id > 0)
            media = (Media) service.get(Media.class, id);
        // if we got the media, but the code is not valid, set media to null
        if ((media != null) && !media.validCode(m))
            media = null;
        Map model = errors.getModel();
        if (media != null) {
            // private media file can not be embedded
            if (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PRIVATE)
                media = null;
            if (media != null) {
                // set locationCode as same as randomCode if locationCode is empty
                if (StringUtils.isBlank(media.getLocationCode()))
                    media.setLocationCode(media.getRandomCode());
                model.put("obj", media);
                // put autoPlay and autoBuffering into data model when necessary
                boolean autoPlay = AUTO_PLAY_DEFAULT;
                boolean autoBuffering = AUTO_BUFFERING_DEFAULT;
                if (request.getParameter("autoPlay") != null)
                    autoPlay = Boolean.valueOf(request.getParameter("autoPlay")).booleanValue();
                if (request.getParameter("autoBuffering") != null)
                    autoBuffering = Boolean.valueOf(request.getParameter("autoBuffering")).booleanValue();
                if (autoPlay != AUTO_PLAY_DEFAULT)
                    model.put("autoPlay", autoPlay);
                if (autoBuffering != AUTO_BUFFERING_DEFAULT)
                    model.put("autoBuffering", autoBuffering);
            }
        }
        response.setContentType("text/javascript");
        return getModelAndView(model, request);
    }

}
