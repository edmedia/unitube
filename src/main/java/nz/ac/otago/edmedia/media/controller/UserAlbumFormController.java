package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.*;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * UserAlbum FormController.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class UserAlbumFormController extends BaseFormController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {
        UserAlbum userAlbum = (UserAlbum) command;
        // deal with user
        Long userID = userAlbum.getUserID();
        if (userID != null) {
            User tmp = (User) service.get(User.class, userID);
            userAlbum.setUser(tmp);
        } else {
            userAlbum.setUser(null);
        }
        // deal with album
        Long albumID = userAlbum.getAlbumID();
        if (albumID != null) {
            Album tmp = (Album) service.get(Album.class, albumID);
            userAlbum.setAlbum(tmp);
        } else {
            userAlbum.setAlbum(null);
        }
        return super.onSubmit(request, response, command, errors);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map model = super.referenceData(request);
        if (model == null)
            model = new HashMap();
        long id = ServletUtil.getParameter(request, "userID", 0);
        User user = null;
        if (id != 0)
            user = (User) service.get(User.class, id);
        if (user != null)
            model.put("user", user);
        else {
            List userList = service.list(User.class);
            model.put("userList", userList);
        }
        List albumList = service.list(Album.class);
        model.put("albumList", albumList);
        return model;
    }

}

