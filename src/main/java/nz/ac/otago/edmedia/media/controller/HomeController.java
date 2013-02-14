package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

/**
 * Home Controller.
 * <p/>
 * Randomly display 5 most visited and 5 most recent media files
 * from 20 media files.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 11/09/2009
 *         Time: 17:00:21
 */
public class HomeController extends BaseOperationController {

    private final static String DATA_FILENAME = "dataHome.data";

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> model = errors.getModel();
        File cacheRoot = MediaUtil.getCacheRoot(getUploadLocation());
        File file = new File(cacheRoot, DATA_FILENAME);
        if (Boolean.parseBoolean(request.getParameter("clearCache")))
            if (!file.delete())
                logger.warn("can't delete cache file " + file.getAbsolutePath());
        if (!file.exists())
            MediaUtil.generateHome(MediaUtil.getFreemarkerConfig(getServletContext()), service, getUploadLocation(), ServletUtil.getContextURL(request));
        String content = IOUtils.toString(new FileReader(file));
        model.put("content", content);
        return getModelAndView(model, request);
    }
}
