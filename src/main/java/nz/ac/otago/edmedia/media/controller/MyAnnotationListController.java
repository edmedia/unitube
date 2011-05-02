package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Annotation;
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
 * Annotation List controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 8/02/2010
 *         Time: 3:44:17 PM
 */
public class MyAnnotationListController extends BaseListController {

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
                .eq("author", user)
                .orderBy("annotName")
                .build();
        Page page = service.pagination(Annotation.class, pageBean.getP(), pageBean.getS(), criteria);
        model.put("pager", page);
        return getModelAndView(model, request);
    }
}
