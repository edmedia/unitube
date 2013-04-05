package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.bean.UserAlbum;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Apply user to album.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 4/07/2008
 *         Time: 09:51:23
 */
public class MyApplyUserController extends BaseOperationController {

    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors) throws Exception {

        long albumID = ServletUtil.getParameter(request, "albumID", 0L);
        String userName = request.getParameter("userName");
        if ((albumID > 0) && StringUtils.isNotBlank(userName)) {
            Album album = (Album) service.get(Album.class, albumID);
            // only get userName part, without "(FirstName LastName)"
            userName = StringUtils.substringBefore(userName, "(").trim();
            @SuppressWarnings("unchecked")
            List<User> users = (List<User>) service.search(User.class, "userName", userName);
            User user = null;
            if (!users.isEmpty())
                user = users.get(0);
            if ((album != null) && (user != null)) {
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
        return getModelAndView(errors.getModel(), request);
    }
}

