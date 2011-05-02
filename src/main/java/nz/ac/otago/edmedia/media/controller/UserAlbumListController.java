package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.page.Page;
import nz.ac.otago.edmedia.page.PageBean;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.spring.controller.BaseListController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * UserAlbum List Controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class UserAlbumListController extends BaseListController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        long id = ServletUtil.getParameter(request, "userID", 0L);
        User user = null;
        if (id > 0)
            user = (User) service.get(User.class, id);
        if (user != null) {
            PageBean pageBean = (PageBean) command;
            Map model = errors.getModel();
            getOrderBy(request, model);
            Page page;
            if (StringUtils.isBlank(getTheOrderBy())) {
                SearchCriteria criteria = new SearchCriteria.Builder()
                        .eq("user", user)
                        .build();
                page = service.pagination(getBeanClass(),
                        pageBean.getP(), pageBean.getS(),
                        criteria);
            } else {
                SearchCriteria criteria = new SearchCriteria.Builder()
                        .eq("user", user)
                        .orderBy(getTheOrderBy(), !isTheOrderByDesc())
                        .build();
                page = service.pagination(getBeanClass(),
                        pageBean.getP(), pageBean.getS(),
                        criteria);
            }
            if (page != null) {
                page.setRequest(request);
                model.put("pager", page);
            }
            model.put("user", user);
            return getModelAndView(model, request);
        } else {
            return super.handle(request, response, command, errors);
        }
    }
}
