package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.auth.util.AuthUtil;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Transfer Owner controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: Mar 29, 2011
 *         Time: 1:47:48 PM
 */
public class TransferOwnerController extends BaseOperationController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors) throws Exception {


        boolean success = false;
        String detail = "Can not find given media or user";

        // get media ids, and userName
        // check if current user is instructor, or the owner of media
        // then transfer owner to given user
        String mediaIds = ServletUtil.getParameter(request, "mediaIds");
        String userName = ServletUtil.getParameter(request, "userName");
        if (StringUtils.isNotBlank(mediaIds) && StringUtils.isNotBlank(userName)) {
            // only get userName part, without "(FirstName LastName)"
            userName = StringUtils.substringBefore(userName, "(").trim();
            @SuppressWarnings("unchecked")
            List<User> users = (List<User>) service.search(User.class, "userName", userName);
            User newOwner = null;
            if (!users.isEmpty())
                newOwner = users.get(0);
            if (newOwner != null) {
                User currentUser = MediaUtil.getCurrentUser(service, request);
                String[] ids = mediaIds.split(",");
                for (String id : ids) {
                    long i = 0;
                    try {
                        i = Long.parseLong(id);
                    } catch (NumberFormatException e) {
                        // ignore
                    }
                    Media media = null;
                    if (i > 0)
                        media = (Media) service.get(Media.class, i);
                    if ((media != null) && (newOwner != null) && (currentUser != null)) {
                        AuthUser authUser = AuthUtil.getAuthUser(request);
                        // if current user is instrucotr, or owner of media
                        if (authUser.getIsInstructor() || (media.getUser().getId().equals(currentUser.getId()))) {
                            User currentOwner = media.getUser();
                            if (MediaUtil.moveMediaFile(media, newOwner, getUploadLocation())) {
                                try {
                                    media.setUser(newOwner);
                                    service.update(media);
                                    success = true;
                                    detail = null;
                                } catch (Exception e) {
                                    logger.error("Exception when updating owner.", e);
                                    // move media files back
                                    MediaUtil.moveMediaFile(media, currentOwner, getUploadLocation());
                                }
                            } else {
                                detail = "Can not move medie files";
                            }
                        } else {
                            detail = "You are not allowed to transfer this media file";
                        }
                    }
                }
            } else {
                detail = "Can not find new owner.";
            }
        }
        OtherUtil.responseXml(response, "transferOwner", success, detail);
        return null;
    }

}

