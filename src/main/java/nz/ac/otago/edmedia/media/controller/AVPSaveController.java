package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.AVP;
import nz.ac.otago.edmedia.media.bean.SlideInfo;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AVP save controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 27/06/11
 *         Time: 4:49 PM
 */
public class AVPSaveController extends BaseOperationController {

    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        boolean success = false;
        String detail = "We have problem to save your data.";
        AVP avp = null;
        Long id = ServletUtil.getParameter(request, "id", 0L);
        if (id > 0)
            avp = (AVP) service.get(AVP.class, id);
        if (avp != null) {
            String timeline = request.getParameter("timeline");
            if (StringUtils.isNotBlank(timeline)) {
                // remove existing slideInfo first
                for (SlideInfo slideInfo : avp.getSlideInfos()) {
                    service.delete(slideInfo);
                }
                JSONParser parser = new JSONParser();
                JSONArray array = (JSONArray) parser.parse(timeline);
                for (Object o : array) {
                    JSONObject obj = (JSONObject) o;
                    SlideInfo slideInfo = new SlideInfo();
                    slideInfo.setsTime(Float.parseFloat(obj.get("sTime").toString()));
                    slideInfo.seteTime(Float.parseFloat(obj.get("eTime").toString()));
                    slideInfo.setNum(Integer.parseInt(obj.get("num").toString()));
                    slideInfo.setTitle(obj.get("title").toString());
                    slideInfo.setAvp(avp);
                    service.save(slideInfo);
                }
                success = true;
            } else {
                detail = "Can not find slide information.";
            }
        } else {
            detail = "Can not find given AVP.";
        }
        OtherUtil.responseXml(response, "saveAVP", success, detail);
        return null;
    }
}
