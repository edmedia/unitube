package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.page.Page;
import nz.ac.otago.edmedia.page.PageBean;
import nz.ac.otago.edmedia.spring.controller.BaseListController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Album list controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 16/06/2008
 *         Time: 16:03:55
 */
public class MyAlbumListController extends BaseListController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        PageBean pageBean = (PageBean) command;
        User user = MediaUtil.getCurrentUser(service, request);
        @SuppressWarnings("unchecked")
        Map<String, Object> model = errors.getModel();
        SearchCriteria criteria = new SearchCriteria.Builder()
                .eq("owner", user)
                .orderBy("albumName")
                .build();
        Page page = service.pagination(Album.class, pageBean.getP(), pageBean.getS(), criteria);
        model.put("pager", page);
        model.put("user", user);
        return getModelAndView(model, request);
    }
}
