package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.page.PageBean;
import nz.ac.otago.edmedia.spring.controller.BaseListController;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Map;

/**
 * UniTubas controller. List all users and their media and albums.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 23/07/2008
 *         Time: 16:52:15
 */
public class UniTubasController extends BaseListController {

    // update cache every 3 days
    private final static long CACHE_UPDATE_INTERVAL = 3 * DateUtils.MILLIS_PER_DAY;
    private final static String DATA_FILENAME = "dataUniTubas-#s-#p.data";

    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> model = errors.getModel();
        PageBean pageBean = (PageBean) command;
        int p = pageBean.getP();
        int s = pageBean.getS();
        if (p == 0)
            p = pageBean.getDefaultPageNumber();
        if (s == 0)
            s = pageBean.getDefaultPageSize();
        File cacheRoot = new File(getUploadLocation().getUploadDir(), "cache");
        File file = new File(cacheRoot, DATA_FILENAME.replace("#s", "" + s).replace("#p", "" + p));
        if (!file.exists() || ((new Date().getTime() - file.lastModified()) > CACHE_UPDATE_INTERVAL))
            file = MediaUtil.generateUniTubas(MediaUtil.getFreemarkerConfig(this.getServletContext()), service, getUploadLocation(), pageBean, ServletUtil.getContextURL(request));
        String content = IOUtils.toString(new FileReader(file));
        model.put("content", content);
        return getModelAndView(model, request);
    }

}
