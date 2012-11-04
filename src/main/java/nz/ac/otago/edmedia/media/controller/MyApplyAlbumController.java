package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.AlbumMedia;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Apply album to selected media.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 19/06/2008
 *         Time: 10:45:10
 */
public class MyApplyAlbumController extends BaseOperationController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors) throws Exception {

        String[] ids = request.getParameterValues("id");
        long albumID = ServletUtil.getParameter(request, "albumID", 0L);
        if ((albumID > 0) && (ids != null) && (ids.length > 0)) {
            Album album = (Album) service.get(Album.class, albumID);
            if (album != null)
                for (String id : ids) {
                    long mediaID = Long.parseLong(id);
                    @SuppressWarnings("unchecked")
                    Media media = (Media) service.get(Media.class, mediaID);
                    if (media != null) {
                        SearchCriteria criteria = new SearchCriteria.Builder()
                                .eq("album", album)
                                .eq("media", media)
                                .build();
                        List list = service.search(AlbumMedia.class, criteria);
                        if (list.isEmpty()) {
                            AlbumMedia albumMedia = new AlbumMedia();
                            albumMedia.setAlbum(album);
                            albumMedia.setMedia(media);
                            AlbumMedia lastAlbumMedia = null;
                            for(AlbumMedia tmp : album.getAlbumMedias())
                            lastAlbumMedia = tmp;
                            if(lastAlbumMedia==null)
                                albumMedia.setOrderNumber(0);
                            else
                            albumMedia.setOrderNumber(lastAlbumMedia.getOrderNumber() +1);
                            service.save(albumMedia);
                        }
                    }
                }
        }
        return getModelAndView(errors.getModel(), request);
    }
}
