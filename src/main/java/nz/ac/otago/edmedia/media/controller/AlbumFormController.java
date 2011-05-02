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
 * Album FormController.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class AlbumFormController extends BaseFormController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {
        Album album = (Album) command;
        // deal with user
        Long ownerID = album.getOwnerID();
        if (ownerID != null) {
            User tmp = (User) service.get(User.class, ownerID);
            album.setOwner(tmp);
        } else {
            album.setOwner(null);
        }
        // deal with albumMedia
        Long[] albumMediasID = album.getAlbumMediasID();
        if (albumMediasID != null) {
            Set ids = new HashSet(Arrays.asList(albumMediasID));
            Set toBeRemove = new HashSet();
            if (album.getAlbumMedias() != null)
                for (Iterator it = album.getAlbumMedias().iterator(); it.hasNext();) {
                    AlbumMedia tmp = (AlbumMedia) it.next();
                    if (ids.contains(tmp.getId())) {
                        ids.remove(tmp.getId());
                    } else {
                        toBeRemove.add(tmp);
                    }
                }
            for (Iterator it = toBeRemove.iterator(); it.hasNext();) {
                AlbumMedia tmp = (AlbumMedia) it.next();
                album.removeAlbumMedias(tmp);
            }
            for (Iterator it = ids.iterator(); it.hasNext();) {
                Long id = (Long) it.next();
                AlbumMedia tmp = (AlbumMedia) service.get(AlbumMedia.class, id);
                album.addAlbumMedias(tmp);
            }
        }
        // deal with userAlbum
        Long[] userAlbumsID = album.getUserAlbumsID();
        if (userAlbumsID != null) {
            Set ids = new HashSet(Arrays.asList(userAlbumsID));
            Set toBeRemove = new HashSet();
            if (album.getUserAlbums() != null)
                for (Iterator it = album.getUserAlbums().iterator(); it.hasNext();) {
                    UserAlbum tmp = (UserAlbum) it.next();
                    if (ids.contains(tmp.getId())) {
                        ids.remove(tmp.getId());
                    } else {
                        toBeRemove.add(tmp);
                    }
                }
            for (Iterator it = toBeRemove.iterator(); it.hasNext();) {
                UserAlbum tmp = (UserAlbum) it.next();
                album.removeUserAlbums(tmp);
            }
            for (Iterator it = ids.iterator(); it.hasNext();) {
                Long id = (Long) it.next();
                UserAlbum tmp = (UserAlbum) service.get(UserAlbum.class, id);
                album.addUserAlbums(tmp);
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
        List ownerList = service.list(User.class);
        model.put("ownerList", ownerList);
        List albumMediasList = service.list(AlbumMedia.class);
        model.put("albumMediasList", albumMediasList);
        List userAlbumsList = service.list(UserAlbum.class);
        model.put("userAlbumsList", userAlbumsList);
        return model;
    }

}

