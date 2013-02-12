package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.page.Page;
import nz.ac.otago.edmedia.page.PageBean;
import nz.ac.otago.edmedia.spring.controller.BaseListController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.CommonUtil;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Media Controller. Display all public media files.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 16/07/2008
 *         Time: 16:19:42
 */
public class MediaController extends BaseListController {

    // update cache every 30 minutes
    private final static long CACHE_UPDATE_INTERVAL = 30 * DateUtils.MILLIS_PER_MINUTE;
    private final static String DATA_FILENAME = "dataMedia-#s-#p.data";
    private final static String TEMPLATE_FILENAME = "dataMedia.ftl";

    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        int mediaType = MediaUtil.MEDIA_TYPE_UNKNOWN;
        // get unique user code from url
        String u = request.getParameter("u");
        User user = null;
        if (u != null)
            user = (User) service.get(User.class, CommonUtil.getId(u));
        // if we got the user, but the code is not valid, set user to null
        if ((user != null) && !user.validCode(u))
            user = null;
        if (user == null) {
            // looking for media type
            mediaType = ServletUtil.getParameter(request, "t", MediaUtil.MEDIA_TYPE_UNKNOWN);
            if ((mediaType != MediaUtil.MEDIA_TYPE_OTHER_MEDIA) &&
                    (mediaType != MediaUtil.MEDIA_TYPE_IMAGE) &&
                    (mediaType != MediaUtil.MEDIA_TYPE_AUDIO) &&
                    (mediaType != MediaUtil.MEDIA_TYPE_VIDEO))
                mediaType = MediaUtil.MEDIA_TYPE_UNKNOWN;
        }
        PageBean pageBean = (PageBean) command;
        @SuppressWarnings("unchecked")
        Map<String, Object> model = errors.getModel();
        // only cache when user is null and media type is unknown
        if ((user == null) && (mediaType == MediaUtil.MEDIA_TYPE_UNKNOWN)) {
            int p = pageBean.getP();
            int s = pageBean.getS();
            if (p == 0)
                p = pageBean.getDefaultPageNumber();
            if (s == 0)
                s = pageBean.getDefaultPageSize();
            File cacheRoot = new File(getUploadLocation().getUploadDir(), "cache");
            if (!cacheRoot.exists()) {
                boolean result = cacheRoot.mkdirs();
                if(!result)
                    logger.error("Failed to create directory " + cacheRoot.getAbsolutePath());
            }
            File file = new File(cacheRoot, DATA_FILENAME.replace("#s", "" + s).replace("#p", "" + p));
            if (!file.exists() || ((new Date().getTime() - file.lastModified()) > CACHE_UPDATE_INTERVAL)) {
                // generate data
                Map<String, Object> dataModel = new HashMap<String, Object>();
                SearchCriteria criteria = new SearchCriteria.Builder()
                        .eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                        .eq("status", MediaUtil.MEDIA_PROCESS_STATUS_FINISHED)
                        .orderBy("uploadTime", false)
                        .build();
                Page page = service.pagination(Media.class, pageBean.getP(), pageBean.getS(), criteria);
                file = new File(cacheRoot, DATA_FILENAME.replace("#s", "" + page.getPageSize()).replace("#p", "" + page.getPageNumber()));
                page.setRequest(request);
                dataModel.put("pager", page);
                dataModel.put("baseUrl", ServletUtil.getContextURL(request));
                dataModel.put("this_url", request.getRequestURI());
                MediaUtil.generateData(this.getServletContext(), dataModel, TEMPLATE_FILENAME, file);
            }
            String content = IOUtils.toString(new FileReader(file));
            model.put("content", content);
        } else {
            // otherwise, search database
            SearchCriteria.Builder builder = new SearchCriteria.Builder();
            builder = builder.eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                    .eq("status", MediaUtil.MEDIA_PROCESS_STATUS_FINISHED)
                    .orderBy("uploadTime", false);
            if (user != null) {
                model.put("user", user);
                builder = builder.eq("user", user);
            } else if (mediaType != MediaUtil.MEDIA_TYPE_UNKNOWN) {
                model.put("mediaType", mediaType);
                builder = builder.eq("mediaType", mediaType);
            }
            SearchCriteria criteria = builder.build();
            Page page = service.pagination(Media.class, pageBean.getP(), pageBean.getS(), criteria);
            model.put("pager", page);
        }
        return getModelAndView(model, request);
    }

}
