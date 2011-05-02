package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.AccessRule;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Delete Access Rule controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: Apr 27, 2011
 *         Time: 1:13:52 PM
 */
public class AccessRuleDeleteController extends BaseOperationController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors) throws Exception {


        boolean success = false;
        String detail = "Can not find given access rule";

        // get media ids, and userName
        // check if current user is instructor, or the owner of media
        // then transfer owner to given user
        AccessRule accessRule = null;
        long id = ServletUtil.getParameter(request, "id", 0L);
        if (id > 0)
            accessRule = (AccessRule) service.get(AccessRule.class, id);
        User currentUser = MediaUtil.getCurrentUser(service, request);
        // only delete access rule if current user is the owner of the media file
        if ((currentUser != null)
                && (accessRule != null)
                && currentUser.getId().equals(accessRule.getMedia().getUser().getId())) {
            try {
                service.delete(accessRule);
                success = true;

            } catch (Exception e) {
                logger.error("Can not delete access rule", e);
            }
        } else {
            detail = "You are not the owner of the given media file.";
        }
        OtherUtil.responseXml(response, "deleteAccessRule", success, detail);
        return null;
    }

}