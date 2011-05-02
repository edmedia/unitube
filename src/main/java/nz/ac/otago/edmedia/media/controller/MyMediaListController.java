package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.bean.UserAlbum;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.page.Page;
import nz.ac.otago.edmedia.page.PageBean;
import nz.ac.otago.edmedia.spring.controller.BaseListController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Media list controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 20/08/2008
 *         Time: 11:29:26
 */
public class MyMediaListController extends BaseListController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        PageBean pageBean = (PageBean) command;
        User user = MediaUtil.getCurrentUser(service, request);
        Map model = errors.getModel();
        SearchCriteria criteria = new SearchCriteria.Builder()
                .eq("user", user)
                .orderBy("uploadTime", false)
                .build();
        Page page = service.pagination(Media.class, pageBean.getP(), pageBean.getS(), criteria);
        List userAlbumList = service.search(UserAlbum.class, "user", user);
        model.put("pager", page);
        model.put("user", user);
        model.put("userAlbumList", userAlbumList);
        return getModelAndView(model, request);
    }
}

