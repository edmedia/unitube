package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Album;
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
import java.util.Map;

/**
 * Albums Controller. List all public albums.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 14/11/2008
 *         Time: 10:31:12
 */
public class AlbumsController extends BaseListController {

    // update cache every 1 day
    private final static long CACHE_UPDATE_INTERVAL = 1 * DateUtils.MILLIS_PER_DAY;
    private final static String DATA_FILENAME = "dataAlbums-#s-#p.data";

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        // get unique user code from url
        String u = request.getParameter("u");
        User user = null;
        if (u != null)
            user = (User) service.get(User.class, CommonUtil.getId(u));
        // if we got the user, but the code is not valid, set user to null
        if ((user != null) && !user.validCode(u))
            user = null;
        PageBean pageBean = (PageBean) command;
        @SuppressWarnings("unchecked")
        Map<String, Object> model = errors.getModel();
        // only cache when user is null
        if (user == null) {
            int p = pageBean.getP();
            int s = pageBean.getS();
            if (p == 0)
                p = pageBean.getDefaultPageNumber();
            if (s == 0)
                s = pageBean.getDefaultPageSize();
            File cacheRoot = MediaUtil.getCacheRoot(getUploadLocation());
            File file = new File(cacheRoot, DATA_FILENAME.replace("#s", "" + s).replace("#p", "" + p));
            if (Boolean.parseBoolean(request.getParameter("clearCache")))
                if (!file.delete())
                    logger.warn("can't delete cache file " + file.getAbsolutePath());
            if (!file.exists() || ((new Date().getTime() - file.lastModified()) > CACHE_UPDATE_INTERVAL))
                file = MediaUtil.generateAlbums(MediaUtil.getFreemarkerConfig(getServletContext()), service, getUploadLocation(), pageBean, ServletUtil.getContextURL(request));
            String content = IOUtils.toString(new FileReader(file));
            model.put("content", content);
        } else {
            SearchCriteria criteria = new SearchCriteria.Builder()
                    .eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                    .eq("owner", user)
                    .sizeGt("albumMedias", 0)
                    .orderBy("albumName").build();
            Page page = service.pagination(Album.class, pageBean.getP(), pageBean.getS(), criteria);
            model.put("pager", page);
        }
        if (user != null) {
            model.put("user", user);
            model.put("title", "Albums from " + user.getFirstName() + " " + user.getLastName());
        } else {
            model.put("title", "Albums");
        }
        return getModelAndView(model, request);
    }

}

