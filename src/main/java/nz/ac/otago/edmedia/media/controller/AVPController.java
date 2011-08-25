package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.AVP;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
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
 * Audio/Video presentation. There could be two audio/video + one presentation at the same time.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: Mar 22, 2011
 *         Time: 2:13:45 PM
 */
public class AVPController extends BaseOperationController {

    private String normalView;

    private String loginView;

    public String getNormalView() {
        return normalView;
    }

    public void setNormalView(String normalView) {
        this.normalView = normalView;
    }

    public String getLoginView() {
        return loginView;
    }

    public void setLoginView(String loginView) {
        this.loginView = loginView;
    }

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        if (request.getRequestURI().contains("/myTube/avp.do")) {
            // "/myTube/avp.do" is only used to make sure user has logged in
            // once they logged in, go to normal view
            String viewName = getNormalView() + "?" + request.getQueryString();
            return new ModelAndView(viewName);
        }
        Map model = errors.getModel();
        model.put("accessDenied", Boolean.FALSE);

        String a = request.getParameter("a");
        if (StringUtils.isNotBlank(a)) {
            // deal with AVP
            long id = CommonUtil.getId(a);
            AVP avp = null;
            if (id > 0)
                avp = (AVP) service.get(AVP.class, id);
            if ((avp != null) && !avp.validCode(a))
                avp = null;
            if (avp != null) {
                if (avp.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PRIVATE) {
                    User user = MediaUtil.getCurrentUser(service, request);
                    // if not login yet
                    if (user == null) {
                        String viewName = getLoginView() + "?" + request.getQueryString();
                        return new ModelAndView(viewName);
                    }
                    if (!MediaUtil.canView(avp, user)) {
                        avp = null;
                        model.put("accessDenied", Boolean.TRUE);
                    }
                }
            }
            if (avp != null) {
                model.put("avp", avp);
            }

        } else {
            // get av1 (audio/video) access code from url
            String av1 = request.getParameter("av1");
            Media audioVideo1 = null;
            if (StringUtils.isBlank(av1))
                av1 = "hYeo7fPJat";
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

            if (audioVideo1 != null) {
                if (StringUtils.isBlank(audioVideo1.getLocationCode()))
                    audioVideo1.setLocationCode(audioVideo1.getRandomCode());
                model.put("obj", audioVideo1);
            }
            if (audioVideo2 != null) {
                if (StringUtils.isBlank(audioVideo2.getLocationCode()))
                    audioVideo2.setLocationCode(audioVideo2.getRandomCode());
                model.put("obj2", audioVideo2);
            }
            if (presentation != null) {
                if (StringUtils.isBlank(presentation.getLocationCode()))
                    presentation.setLocationCode(presentation.getRandomCode());
                model.put("presentation", presentation);
            }
            model.put("xml", xml);
        }
        if (request.getRequestURI().contains("avpSync.do"))
            model.put("title", "Audio/Video Presentation Synchronisation");
        else
            model.put("title", "Audio/Video Presentation");
        return getModelAndView(model, request);
    }
}
