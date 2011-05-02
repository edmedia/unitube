package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.*;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * AccessRule FormController.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class AccessRuleFormController extends BaseFormController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {
        AccessRule accessRule = (AccessRule) command;
        // deal with media
        Long mediaID = accessRule.getMediaID();
        if (mediaID != null) {
            Media tmp = (Media) service.get(Media.class, mediaID);
            accessRule.setMedia(tmp);
        } else {
            accessRule.setMedia(null);
        }
        // deal with user
        Long userID = accessRule.getUserID();
        if (userID != null) {
            User tmp = (User) service.get(User.class, userID);
            accessRule.setUser(tmp);
        } else {
            accessRule.setUser(null);
        }
        return super.onSubmit(request, response, command, errors);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map model = super.referenceData(request);
        if (model == null)
            model = new HashMap();
        List mediaList = service.list(Media.class);
        model.put("mediaList", mediaList);
        List userList = service.list(User.class);
        model.put("userList", userList);
        return model;
    }

}

