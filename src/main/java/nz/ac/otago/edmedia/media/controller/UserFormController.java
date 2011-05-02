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
 * User FormController.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class UserFormController extends BaseFormController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {
        User user = (User) command;
        // deal with media
        Long[] mediasID = user.getMediasID();
        if (mediasID != null) {
            Set ids = new HashSet(Arrays.asList(mediasID));
            Set toBeRemove = new HashSet();
            if (user.getMedias() != null)
                for (Iterator it = user.getMedias().iterator(); it.hasNext();) {
                    Media tmp = (Media) it.next();
                    if (ids.contains(tmp.getId())) {
                        ids.remove(tmp.getId());
                    } else {
                        toBeRemove.add(tmp);
                    }
                }
            for (Iterator it = toBeRemove.iterator(); it.hasNext();) {
                Media tmp = (Media) it.next();
                user.removeMedias(tmp);
            }
            for (Iterator it = ids.iterator(); it.hasNext();) {
                Long id = (Long) it.next();
                Media tmp = (Media) service.get(Media.class, id);
                user.addMedias(tmp);
            }
        }
        // deal with userAlbum
        Long[] userAlbumsID = user.getUserAlbumsID();
        if (userAlbumsID != null) {
            Set ids = new HashSet(Arrays.asList(userAlbumsID));
            Set toBeRemove = new HashSet();
            if (user.getUserAlbums() != null)
                for (Iterator it = user.getUserAlbums().iterator(); it.hasNext();) {
                    UserAlbum tmp = (UserAlbum) it.next();
                    if (ids.contains(tmp.getId())) {
                        ids.remove(tmp.getId());
                    } else {
                        toBeRemove.add(tmp);
                    }
                }
            for (Iterator it = toBeRemove.iterator(); it.hasNext();) {
                UserAlbum tmp = (UserAlbum) it.next();
                user.removeUserAlbums(tmp);
            }
            for (Iterator it = ids.iterator(); it.hasNext();) {
                Long id = (Long) it.next();
                UserAlbum tmp = (UserAlbum) service.get(UserAlbum.class, id);
                user.addUserAlbums(tmp);
            }
        }
        return super.onSubmit(request, response, command, errors);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map model = super.referenceData(request);
        if (model == null)
            model = new HashMap();
        List mediasList = service.list(Media.class);
        model.put("mediasList", mediasList);
        List userAlbumsList = service.list(UserAlbum.class);
        model.put("userAlbumsList", userAlbumsList);
        return model;
    }

}

