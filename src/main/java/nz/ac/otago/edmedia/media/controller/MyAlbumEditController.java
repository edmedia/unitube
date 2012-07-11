package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.bean.UserAlbum;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Album FormController.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 5/07/2007
 *         Time: 14:46:44
 */
public class MyAlbumEditController extends BaseFormController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {

        Album album = (Album) command;
        User user = MediaUtil.getCurrentUser(service, request);
        // when create a new Album, generate access code
        if (album.getId() == null) {
            album.setOwner(user);
            String randomCode = CommonUtil.generateRandomCode();
            album.setRandomCode(randomCode);
            // remove script tag in description
            if (StringUtils.isNotBlank(album.getDescription())) {
                String desc = MediaUtil.removeScriptTag(album.getDescription());
                album.setDescription(desc);
            }
            service.save(album);

            UserAlbum userAlbum = new UserAlbum();
            userAlbum.setUser(user);
            userAlbum.setAlbum(album);
            service.save(userAlbum);

        } else {
            // update Album
            Album oldAlbum = (Album) service.get(Album.class, album.getId());
            oldAlbum.setAlbumName(album.getAlbumName());
            // remove script tag in description
            if (StringUtils.isNotBlank(album.getDescription())) {
                String desc = MediaUtil.removeScriptTag(album.getDescription());
                album.setDescription(desc);
            }
            oldAlbum.setDescription(album.getDescription());
            service.update(oldAlbum);
        }
        Map model = errors.getModel();
        return getModelAndView(model, request);
    }

}

