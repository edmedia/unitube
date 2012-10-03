package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.AlbumMedia;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Re-order album media files.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 3/10/12
 *         Time: 11:12 AM
 */
public class AlbumMediaReOrderController extends BaseOperationController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {
        String action = "albumMediaReOrder";
        // get user info
        User user = MediaUtil.getCurrentUser(service, request);
        boolean result = true;
        long albumId = ServletUtil.getParameter(request, "albumId", 0L);
        Album album = (Album) service.get(Album.class, albumId);
        // if album exists and current user is owner, do re-order
        if ((album != null) && (user != null) && album.getOwner().getId().equals(user.getId())) {
            String data = request.getParameter("data");
            JSONParser parser = new JSONParser();
            @SuppressWarnings("unchecked")
            JSONArray array = (JSONArray) parser.parse(data);
            for (Object obj : array) {
                @SuppressWarnings("unchecked")
                JSONObject json = (JSONObject) obj;
                try {
                    long id = Long.valueOf(json.get("id").toString());
                    int orderNumber = Integer.valueOf(json.get("orderNumber").toString());
                    AlbumMedia albumMedia = (AlbumMedia) service.get(AlbumMedia.class, id);
                    if (albumMedia != null) {
                        albumMedia.setOrderNumber(orderNumber);
                        try {
                            service.update(albumMedia);
                        } catch (Exception e) {
                            logger.error("Exception when updating album media", e);
                            result = false;
                        }
                    }
                } catch (NumberFormatException nfe) {
                    logger.error("NumberFormatException", nfe);
                }
            }
        }
        OtherUtil.responseXml(response, action, result);
        return null;
    }
}
