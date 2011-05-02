package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.page.Page;
import nz.ac.otago.edmedia.page.PageBean;
import nz.ac.otago.edmedia.spring.controller.BaseListController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Albums Controller. List all public albums.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 14/11/2008
 *         Time: 10:31:12
 */
public class AlbumsController extends BaseListController {

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        // get unique user code from url
        String u = request.getParameter("u");
        User user = null;
        if (u != null)
            user = (User) service.get(User.class, CommonUtil.getId(u));
        // if we got the user, but the code is not valid, set user to null
        if ((user != null) && !user.validCode(u))
            user = null;
        PageBean pageBean = (PageBean) command;
        Map model = errors.getModel();
        SearchCriteria.Builder builder = new SearchCriteria.Builder();
        builder = builder.eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                .sizeGt("albumMedias", 0)
                .orderBy("albumName");
        if (user != null)
            builder = builder.eq("owner", user);
        SearchCriteria criteria = builder.build();
        Page page = service.pagination(Album.class, pageBean.getP(), pageBean.getS(), criteria);
        model.put("pager", page);
        if (user != null) {
            model.put("user", user);
            model.put("title", "Albums from " + user.getFirstName() + " " + user.getLastName());
        } else {
            model.put("title", "Albums");
        }
        return getModelAndView(model, request);
    }

}

