package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Audio/Video presentation. There could be two audio/video at the same time.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: Mar 22, 2011
 *         Time: 2:13:45 PM
 */
public class AVPController extends BaseOperationController {

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        // get av1 (audio/video) access code from url
        String av1 = request.getParameter("av1");
        Media audioVideo1 = null;
        if (StringUtils.isBlank(av1))
            av1 = "sBXp6nYV8A";
        if (StringUtils.isNotBlank(av1)) {
            // get id from access code
            long id = CommonUtil.getId(av1);
            if (id > 0)
                audioVideo1 = (Media) service.get(Media.class, id);
            if ((audioVideo1 != null) && !audioVideo1.validCode(av1))
                audioVideo1 = null;
        }

        // get av2 (audio/video) access code from url
        String av2 = request.getParameter("av2");
        Media audioVideo2 = null;
        if (StringUtils.isNotBlank(av2)) {
            // get id from access code
            long id = CommonUtil.getId(av2);
            if (id > 0)
                audioVideo2 = (Media) service.get(Media.class, id);
            if ((audioVideo2 != null) && !audioVideo2.validCode(av2))
                audioVideo2 = null;
        }

        Media presentation = null;
        // get p (presentation) access code from url
        String p = request.getParameter("p");
        if (StringUtils.isBlank(p))
            p = "Ue456pLvHG";
        if (StringUtils.isNotBlank(p)) {
            long id = CommonUtil.getId(p);
            if (id > 0)
                presentation = (Media) service.get(Media.class, id);
            if ((presentation != null) && !presentation.validCode(p))
                presentation = null;
        }

        String xml = request.getParameter("xml");
        if (StringUtils.isBlank(xml))
            xml = "avpTest.xml";

        Map model = errors.getModel();
        if (audioVideo1 != null) {
            if (StringUtils.isBlank(audioVideo1.getLocationCode()))
                audioVideo1.setLocationCode(audioVideo1.getRandomCode());
            model.put("obj", audioVideo1);
            model.put("av1", av1);
        }
        if (audioVideo2 != null) {
            if (StringUtils.isBlank(audioVideo2.getLocationCode()))
                audioVideo2.setLocationCode(audioVideo2.getRandomCode());
            model.put("obj2", audioVideo2);
            model.put("av2", av2);
        }
        if (presentation != null) {
            if (StringUtils.isBlank(presentation.getLocationCode()))
                presentation.setLocationCode(presentation.getRandomCode());
            model.put("presentation", presentation);
            model.put("p", p);
        }
        model.put("xml", xml);
        model.put("title", "Audio/Video Presentation");
        return getModelAndView(model, request);
    }
}
