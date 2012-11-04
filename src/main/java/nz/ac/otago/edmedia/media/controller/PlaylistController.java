package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.AlbumMedia;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Playlist controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 31/10/12
 *         Time: 11:29 AM
 */
public class PlaylistController extends BaseOperationController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> model = (Map<String, Object>) errors.getModel();
        // get access code from url
        String a = request.getParameter("a");
        Album album = null;
        if (a != null)
            album = (Album) service.get(Album.class, CommonUtil.getId(a));
        if ((album != null) && album.validCode(a)) {
            List<Media> playlist = new ArrayList<Media>();
            for (AlbumMedia am : album.getAlbumMedias()) {
                // only list hidden media in hidden album
                if ((am.getMedia().getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                        || ((am.getMedia().getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_HIDDEN) && (album.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_HIDDEN)))
                    playlist.add(am.getMedia());
            }
            model.put("album", album);
            model.put("playlist", playlist);
            model.put("isOwner", false);
            model.put("title", "Playlist");
            // get user info
            User user = MediaUtil.getCurrentUser(service, request);
            if ((user != null) && album.getOwner().getId().equals(user.getId()))
                model.put("isOwner", true);
        }
        return getModelAndView(model, request);
    }
}
