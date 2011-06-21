package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.AlbumMedia;
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
 * Remove album media controller.
 * Removes media file from an album
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 19/06/2008
 *         Time: 16:07:14
 */
public class MyRemoveAlbumMediaController extends BaseOperationController {

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
        AlbumMedia albumMedia = null;
        if (id > 0)
            albumMedia = (AlbumMedia) service.get(AlbumMedia.class, id);
        if ((user != null) && (albumMedia != null)) {
            if (albumMedia.getMedia().getUser().getId().equals(user.getId())) {
                service.delete(albumMedia);
                success = true;
            } else
                detail = "You are not the owner of the media file.";
        } else {
            if (user == null)
                detail = "You are not logged in yet. Please login first.";
            else
                detail = "Can not find this album media.";
        }
        OtherUtil.responseXml(response, "removeAlbumFromMedia", success, detail);
        return null;
    }
}

