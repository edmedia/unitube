package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Create Lucene Index.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 3/12/2008
 *         Time: 16:21:37
 */
public class CreateIndexController extends BaseOperationController {

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        Session session = service.getSession();

        FullTextSession fullTextSession = Search.createFullTextSession(session);
        List<Media> mediaList = (List<Media>) service.list(Media.class);
        for (Media m : mediaList) {
            fullTextSession.index(m);
        }
        List<Album> albumList = (List<Album>) service.list(Album.class);
        for (Album a : albumList) {
            fullTextSession.index(a);
        }
        List<User> userList = (List<User>) service.list(User.class);
        for (User u : userList) {
            fullTextSession.index(u);
        }

        Map model = errors.getModel();
        return getModelAndView(model, request);

    }
}
