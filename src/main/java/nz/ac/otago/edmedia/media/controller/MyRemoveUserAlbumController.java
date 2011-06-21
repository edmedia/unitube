package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.bean.UserAlbum;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Remove user album controller.
 * Removes a user from an album.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 4/07/2008
 *         Time: 09:52:10
 */
public class MyRemoveUserAlbumController extends BaseOperationController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors) throws Exception {

        boolean success = false;
        String detail = null;
        long id = ServletUtil.getParameter(request, "id", 0L);
        // get user info
        User user = MediaUtil.getCurrentUser(service, request);
        UserAlbum userAlbum = null;
        if (id > 0)
            userAlbum = (UserAlbum) service.get(UserAlbum.class, id);
        if ((user != null) && (userAlbum != null)) {
            // only allowed if this album belongs to current user
            if (userAlbum.getAlbum().getOwner().getId().equals(user.getId())
                    && !userAlbum.getUser().getId().equals(user.getId())) {
                service.delete(userAlbum);
                success = true;
            } else
                detail = "You are not the owner of the album.";
        } else {
            if (user == null)
                detail = "You are not logged in yet. Please login first.";
            else
                detail = "Can not find this user album.";
        }
        OtherUtil.responseXml(response, "removeUserFromAlbum", success, detail);
        return null;
    }
}


