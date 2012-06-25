package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.AccessRule;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Add Access Rule controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: Apr 27, 2011
 *         Time: 11:27:35 AM
 */
public class AccessRuleAddController extends BaseOperationController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors) throws Exception {


        boolean success = false;
        String detail = "Can not find given media or user.";

        // get media ids, and userName
        // check if current user is instructor, or the owner of media
        // then transfer owner to given user
        Media media = null;
        long mediaId = ServletUtil.getParameter(request, "mediaID", 0L);
        User currentUser = MediaUtil.getCurrentUser(service, request);
        if (mediaId > 0)
            media = (Media) service.get(Media.class, mediaId);
        if (media != null) {
            // make sure current user is the owner of the media file
            if (!media.getUser().getId().equals(currentUser.getId())) {
                media = null;
                detail = "You are not the owner of this media file.";
            }
        }
        String userInput = ServletUtil.getParameter(request, "userInput");
        if ((media != null) && StringUtils.isNotBlank(userInput)) {
            // only get userName part, without "(FirstName LastName)"
            String userName = StringUtils.substringBefore(userInput, "(").trim();
            @SuppressWarnings("unchecked")
            List<User> users = (List<User>) service.search(User.class, "userName", userName);
            User user = null;
            if (!users.isEmpty())
                user = users.get(0);
            AccessRule accessRule = new AccessRule();
            accessRule.setMedia(media);
            accessRule.setUserInput(userInput);
            // check if this access rule exists already
            if (user != null) {
                // trying to add owner
                if (user.getId().equals(currentUser.getId())) {
                    accessRule = null;
                    detail = "Owner already has full access to this media file.";
                } else {
                    accessRule.setUser(user);
                    SearchCriteria criteria = new SearchCriteria.Builder()
                            .eq("media", media)
                            .eq("user", user)
                            .build();
                    @SuppressWarnings("unchecked")
                    List<AccessRule> list = (List<AccessRule>) service.search(AccessRule.class, criteria);
                    // if exists already, don't create another one
                    if (!list.isEmpty()) {
                        accessRule = null;
                        detail = "You already granted access to this user.";
                    }
                }
            } else {
                SearchCriteria criteria = new SearchCriteria.Builder()
                        .eq("media", media)
                        .eq("userInput", userInput)
                        .build();
                @SuppressWarnings("unchecked")
                List<AccessRule> list = (List<AccessRule>) service.search(AccessRule.class, criteria);
                if (!list.isEmpty()) {
                    accessRule = null;
                    detail = "You already granted access to this user.";
                }
            }
            try {
                if (accessRule != null) {
                    service.save(accessRule);
                    success = true;
                    if (user != null) {
                        String[] keys = {"id", "userName", "firstName", "lastName", "email"};
                        String[] values = {Long.toString(accessRule.getId()), user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail()};
                        StringBuilder sb = new StringBuilder();
                        sb.append("{");
                        for (int i = 0; i < keys.length; i++) {
                            String key = keys[i];
                            String value = values[i];
                            if (i != 0)
                                sb.append(", ");
                            sb.append("\"");
                            sb.append(key);
                            sb.append("\":\"");
                            sb.append(value);
                            sb.append("\"");
                        }
                        sb.append("}");
                        detail = sb.toString();
                    } else {
                        String[] keys = {"id", "userName"};
                        String[] values = {Long.toString(accessRule.getId()), userName};
                        StringBuilder sb = new StringBuilder();
                        sb.append("{");
                        for (int i = 0; i < keys.length; i++) {
                            String key = keys[i];
                            String value = values[i];
                            if (i != 0)
                                sb.append(", ");
                            sb.append("\"");
                            sb.append(key);
                            sb.append("\":\"");
                            sb.append(value);
                            sb.append("\"");
                        }
                        sb.append("}");
                        detail = sb.toString();
                    }
                }
            } catch (Exception e) {
                logger.error("Can not save access rule", e);
            }
        }
        OtherUtil.responseXml(response, "addAccessRule", success, detail);
        return null;
    }

}

