package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.bean.UserAlbum;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.bean.IDListBean;
import nz.ac.otago.edmedia.spring.controller.BaseDeleteController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Remove user album controller.
 * Removes an user from an album.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 4/07/2008
 *         Time: 09:52:10
 */
public class MyRemoveUserAlbumController extends BaseDeleteController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors) throws Exception {

        boolean success = false;
        if (command != null) {
            // get user info
            User user = MediaUtil.getCurrentUser(service, request);

            IDListBean idList = (IDListBean) command;
            if (idList.getId() != null) {
                for (int i = 0; i < idList.getId().length; i++) {
                    UserAlbum userAlbum = (UserAlbum) service.get(getBeanClass(), idList.getId()[i]);
                    // only allowed if this album belongs to current user
                    if ((userAlbum != null) &&
                            userAlbum.getAlbum().getOwner().getUserName().equals(user.getUserName()) &&
                            !userAlbum.getUser().getUserName().equals(user.getUserName())
                            ) {
                        service.delete(userAlbum);
                        success = true;
                    }
                }
            }
        }
        OtherUtil.responseXml(response, "removeUserFromAlbum", success);
        return null;
    }
}


