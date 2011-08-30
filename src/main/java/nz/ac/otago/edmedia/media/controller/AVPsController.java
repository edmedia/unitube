package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.AVP;
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
 * List all public AVPs.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 18/08/11
 *         Time: 9:59 AM
 */
public class AVPsController extends BaseListController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        PageBean pageBean = (PageBean) command;
        Map model = errors.getModel();
        SearchCriteria criteria = new SearchCriteria.Builder()
                .eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                .orderBy("id", false)
                .build();
        Page page = service.pagination(AVP.class, pageBean.getP(), pageBean.getS(), criteria);
        model.put("pager", page);
        model.put("title", "All Audio/Video Presentations");

        return getModelAndView(model, request);
    }
}
