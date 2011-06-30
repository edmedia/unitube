package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.AVP;
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
 * AVP list controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: June 27, 2011
 *         Time: 2:06 PM
 */
public class AVPListController extends BaseListController {

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
                .eq("owner", user)
                .build();
        Page page = service.pagination(AVP.class, pageBean.getP(), pageBean.getS(), criteria);
        model.put("pager", page);
        model.put("user", user);
        return getModelAndView(model, request);
    }
}