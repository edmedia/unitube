package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseListController;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Search media in UniTube.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 5/12/2008
 *         Time: 16:47:28
 */
public class SearchController extends BaseListController {

    private final static String QUERY_KEY = "q";
    private final static String MEDIA_TYPE_KEY = "t";

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        //PageBean pageBean = (PageBean) command;
        Map model = errors.getModel();
        // get query words from request
        String words = ServletUtil.getParameter(request, QUERY_KEY);
        int mediaType = ServletUtil.getParameter(request, MEDIA_TYPE_KEY, MediaUtil.MEDIA_TYPE_UNKNOWN);
        if (StringUtils.isBlank(words)) {
            // if not exist, get query words from session
            words = ServletUtil.getAttribute(request.getSession(), QUERY_KEY);
        }
        if (StringUtils.isNotBlank(words)) {
            // put query words into session
            request.getSession().setAttribute(QUERY_KEY, words.trim());
            model.put("searchWords", words.trim());
            if (mediaType != MediaUtil.MEDIA_TYPE_UNKNOWN) {
                model.put("mediaType", mediaType);
            }
            if (!words.endsWith("*"))
                words += "*";
            List mediaList = MediaUtil.searchMedia(words, service.getSession(), mediaType);
            //Page page = service.pagination(fullTextQuery, pageBean.getP(), pageBean.getS());
            //logger.debug("result size is " + page.getTotalNumberOfElements());
            //model.put("pager", page);
            if (mediaList != null)
                model.put("mediaList", mediaList);

            // only search for album and user when media type is all
            if (mediaType == MediaUtil.MEDIA_TYPE_UNKNOWN) {
                List albumList = MediaUtil.searchAlbum(words, service.getSession());
                if (albumList != null)
                    model.put("albumList", albumList);

                List userList = MediaUtil.searchUser(words, service.getSession());
                if (userList != null)
                    model.put("userList", userList);
            }
        }
        model.put("title", "Search Results");
        return getModelAndView(model, request);
    }
}

