package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        // get access code from url
        String a = request.getParameter("a");
        Map model = errors.getModel();
        Album album = null;
        if (a != null)
            album = (Album) service.get(Album.class, CommonUtil.getId(a));
        // if we got the album, but the code is not valid, set album to null
        if ((album != null) && !album.validCode(a))
            album = null;
        if (album != null)
            model.put("obj", album);
        if (album != null)
            model.put("title", "View Album");
        return getModelAndView(model, request);
    }
}
