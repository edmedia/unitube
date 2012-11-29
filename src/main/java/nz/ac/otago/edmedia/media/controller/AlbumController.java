package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.AlbumMedia;
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
 * Album controller. List all medias in an album.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 17/06/2008
 *         Time: 16:18:20
 */
public class AlbumController extends BaseOperationController {

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
            List<AlbumMedia> list = new ArrayList<AlbumMedia>();
            for (AlbumMedia am : album.getAlbumMedias()) {
                    if(MediaUtil.isVisible(am.getAlbum(), am.getMedia()))
                    list.add(am);
            }
            model.put("list", list);
            model.put("obj", album);
            model.put("isOwner", false);
            // get user info
            User user = MediaUtil.getCurrentUser(service, request);
            if ((user != null) && album.getOwner().getId().equals(user.getId()))
                model.put("isOwner", true);
            model.put("title", "View Album");
        }
        return getModelAndView(model, request);
    }
}
