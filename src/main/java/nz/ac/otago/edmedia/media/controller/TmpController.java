package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Tmp controller to generate date required by Jenny.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 5/08/2010
 *         Time: 4:26:58 PM
 *         To change this template use File | Settings | File Templates.
 */
public class TmpController extends BaseOperationController {

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {
        Map model = errors.getModel();
        List list = service.list(Media.class, "uploadTime");
        model.put("list", list);
        return getModelAndView(model, request);
    }

}
