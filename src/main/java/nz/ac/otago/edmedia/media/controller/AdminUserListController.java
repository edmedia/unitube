package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.page.Page;
import nz.ac.otago.edmedia.page.PageBean;
import nz.ac.otago.edmedia.spring.controller.BaseListController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Admin user list controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 6/03/13
 *         Time: 10:41 AM
 */
public class AdminUserListController extends BaseListController {

    String originalViewName;

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        PageBean pageBean = (PageBean) command;
        @SuppressWarnings("unchecked")
        Map<String, Object> model = errors.getModel();
        getOrderBy(request, model);
        String n = ServletUtil.getParameter(request, "n");
        if (StringUtils.isNotBlank(n)) {
            List users = MediaUtil.searchUser(n, service.getSession());
            model.put("n", n);
            model.put("users", users);
            model.put("title", "User search result");
            // get original view name
            if (!"main".equals(getViewName()))
                originalViewName = getViewName();
            setViewName("main");
        } else {
            SearchCriteria.Builder builder = new SearchCriteria.Builder();
            if (StringUtils.isNotBlank(getTheOrderBy()))
                builder.orderBy(getTheOrderBy(), !isTheOrderByDesc());
            SearchCriteria criteria = builder.build();
            Page page = service.pagination(getBeanClass(),
                    pageBean.getP(), pageBean.getS(),
                    criteria);
            page.setRequest(request);
            model.put("pager", page);
            if (StringUtils.isNotBlank(originalViewName))
                setViewName(originalViewName);
        }
        return getModelAndView(model, request);
    }

}
