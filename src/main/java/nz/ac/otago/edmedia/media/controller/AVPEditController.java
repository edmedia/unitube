package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.AVP;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AVP Form controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: June 27, 2011
 *         Time: 11:14 AM
 */
public class AVPEditController extends BaseFormController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {
        AVP avp = (AVP) command;
        // deal with av1
        Long av1ID = avp.getAv1ID();
        if (av1ID > 0) {
            Media tmp = (Media) service.get(Media.class, av1ID);
            avp.setAv1(tmp);
        } else {
            avp.setAv1(null);
        }
        // deal with av2
        Long av2ID = avp.getAv2ID();
        if (av2ID > 0) {
            Media tmp = (Media) service.get(Media.class, av2ID);
            avp.setAv2(tmp);
        } else {
            avp.setAv2(null);
        }

        // deal with presentation
        Long presentationID = avp.getPresentationID();
        if (presentationID >0) {
            Media tmp = (Media) service.get(Media.class, presentationID);
            avp.setPresentation(tmp);
        } else {
            avp.setPresentation(null);
        }
        // when create a new AVP, generate access code
        if (avp.getId() == null) {
            User user = MediaUtil.getCurrentUser(service, request);
            avp.setOwner(user);
            String randomCode = CommonUtil.generateRandomCode();
            avp.setRandomCode(randomCode);
            service.save(avp);
        } else {
            service.update(avp);
        }
        Map model = errors.getModel();
        return getModelAndView(model, request);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map model = super.referenceData(request);
        if (model == null)
            model = new HashMap();
        User user = MediaUtil.getCurrentUser(service, request);
        SearchCriteria avCriteria = new SearchCriteria.Builder()
                .eq("user", user)
                .ge("mediaType", MediaUtil.MEDIA_TYPE_AUDIO)
                .orderBy("id", true)
                .build();
        List avList = service.search(Media.class, avCriteria);
        model.put("avList", avList);
        SearchCriteria pCriteria = new SearchCriteria.Builder()
                .eq("user", user)
                .eq("mediaType", MediaUtil.MEDIA_TYPE_OTHER_MEDIA)
                .orderBy("id", true)
                .build();
        List pList = service.search(Media.class, pCriteria);
        model.put("pList", pList);
        return model;
    }

}

