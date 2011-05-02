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
 * AlbumMedia FormController.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class AlbumMediaFormController extends BaseFormController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {
        AlbumMedia albumMedia = (AlbumMedia) command;
        // deal with album
        Long albumID = albumMedia.getAlbumID();
        if (albumID != null) {
            Album tmp = (Album) service.get(Album.class, albumID);
            albumMedia.setAlbum(tmp);
        } else {
            albumMedia.setAlbum(null);
        }
        // deal with media
        Long mediaID = albumMedia.getMediaID();
        if (mediaID != null) {
            Media tmp = (Media) service.get(Media.class, mediaID);
            albumMedia.setMedia(tmp);
        } else {
            albumMedia.setMedia(null);
        }
        return super.onSubmit(request, response, command, errors);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map model = super.referenceData(request);
        if (model == null)
            model = new HashMap();
        long id = ServletUtil.getParameter(request, "albumID", 0);
        Album album = null;
        if (id != 0)
            album = (Album) service.get(Album.class, id);
        if (album != null)
            model.put("album", album);
        else {
            List albumList = service.list(Album.class);
            model.put("albumList", albumList);
        }
        List mediaList = service.list(Media.class);
        model.put("mediaList", mediaList);
        return model;
    }

}

