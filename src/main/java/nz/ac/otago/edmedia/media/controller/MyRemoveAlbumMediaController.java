package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.AlbumMedia;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.bean.IDListBean;
import nz.ac.otago.edmedia.spring.controller.BaseDeleteController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Remove album media controller.
 * Removes an album from a media.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 19/06/2008
 *         Time: 16:07:14
 */
public class MyRemoveAlbumMediaController extends BaseDeleteController {

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
                    AlbumMedia albumMedia = (AlbumMedia) service.get(getBeanClass(), idList.getId()[i]);
                    // only allowed if this media belongs to current user
                    if ((albumMedia != null) &&
                            albumMedia.getMedia().getUser().getUserName().equals(user.getUserName())
                            ) {
                        service.delete(albumMedia);
                        success = true;
                    }
                }
            }
        }
        OtherUtil.responseXml(response, "removeAlbumFromMedia", success);
        return null;
    }
}

