package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.User;
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
 * UniTubas controller. List all users and their media and albums.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 23/07/2008
 *         Time: 16:52:15
 */
public class UniTubasController extends BaseListController {

    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        PageBean pageBean = (PageBean) command;
        @SuppressWarnings("unchecked")
        Map<String, Object> model = (Map<String, Object>) errors.getModel();
        SearchCriteria criteria = new SearchCriteria.Builder()
                .eq("isGuest", false)
                .sizeGt("medias", 0)
                .orderBy("lastName")
                .orderBy("firstName")
                .build();
        Page page = service.pagination(User.class, pageBean.getP(), pageBean.getS(), criteria);
        model.put("pager", page);
        return getModelAndView(model, request);
    }

}
