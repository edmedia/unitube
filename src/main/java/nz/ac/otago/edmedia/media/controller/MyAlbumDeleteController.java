package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.bean.UserAlbum;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.bean.IDListBean;
import nz.ac.otago.edmedia.spring.controller.BaseDeleteController;
import nz.ac.otago.edmedia.spring.util.ModelUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Album delete controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 17/06/2008
 *         Time: 10:31:29
 */
public class MyAlbumDeleteController extends BaseDeleteController {

    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        if (command != null) {
            // get user info
            User user = MediaUtil.getCurrentUser(service, request);

            IDListBean idList = (IDListBean) command;
            if (idList.getId() != null) {
                for (int i = 0; i < idList.getId().length; i++) {
                    Album album = (Album) service.get(Album.class, idList.getId()[i]);
                    // only owner can delete their own album
                    if ((album != null) && album.getOwner().getUserName().equals(user.getUserName())) {
                        List userAlbumList = service.search(UserAlbum.class, "album", album);
                        if (userAlbumList != null) {
                            for (int j = 0; j < userAlbumList.size(); j++) {
                                UserAlbum userAlbum = (UserAlbum) userAlbumList.get(j);
                                service.delete(userAlbum);
                            }
                        }
                        service.delete(album);
                    }
                }
            }
        }
        return getModelAndView(errors.getModel(), request);
    }

}
