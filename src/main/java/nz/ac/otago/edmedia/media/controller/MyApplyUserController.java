package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.bean.UserAlbum;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Applys user to album.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 4/07/2008
 *         Time: 09:51:23
 */
public class MyApplyUserController extends BaseOperationController {

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors) throws Exception {

        String[] ids = request.getParameterValues("id");
        long userID = ServletUtil.getParameter(request, "userID", 0L);
        if ((userID > 0) && (ids != null) && (ids.length > 0)) {
            User user = (User) service.get(User.class, userID);
            if (user != null)
                for (int i = 0; i < ids.length; i++) {
                    long albumID = Long.parseLong(ids[i]);
                    Album album = (Album) service.get(Album.class, albumID);
                    if (album != null) {
                        SearchCriteria criteria = new SearchCriteria.Builder()
                                .eq("user", user)
                                .eq("album", album)
                                .build();
                        List list = service.search(UserAlbum.class, criteria);
                        if (list.isEmpty()) {
                            UserAlbum userAlbum = new UserAlbum();
                            userAlbum.setUser(user);
                            userAlbum.setAlbum(album);
                            service.save(userAlbum);
                        }
                    }
                }
        }
        return getModelAndView(errors.getModel(), request);
    }
}

