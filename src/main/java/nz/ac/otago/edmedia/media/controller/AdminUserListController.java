package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.page.Page;
import nz.ac.otago.edmedia.page.PageBean;
import nz.ac.otago.edmedia.spring.controller.BaseListController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.CommonUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Admin user list controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 6/03/13
 *         Time: 10:41 AM
 */
public class AdminUserListController extends BaseListController {

    private static final String VIEW_URL = "/view?m=";
    String originalViewName;

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        PageBean pageBean = (PageBean) command;
        @SuppressWarnings("unchecked")
        Map<String, Object> model = errors.getModel();
        getOrderBy(request, model);
        String words = ServletUtil.getParameter(request, SearchController.QUERY_KEY);
        if (StringUtils.isNotBlank(words)) {
            // if search for a UniTube url, such as http://unitube.otago.ac.nz/view?m=abcd12345678
            if (words.contains(VIEW_URL)) {
                model.put("adminSearchWords", words);
                String m = StringUtils.substringAfter(words, VIEW_URL);
                m = StringUtils.substringBefore(m, "&");
                m = m.trim();
                long id = CommonUtil.getId(m);
                Media media = null;
                if (id > 0)
                    media = (Media) service.get(Media.class, id);
                // if we got the media, but the code is not valid, set media to null
                if ((media != null) && !media.validCode(m))
                    media = null;
                if (media != null) {
                    List<Media> mediaList = new ArrayList<Media>();
                    mediaList.add(media);
                    model.put("mediaList", mediaList);
                }
            } else {
                // assume it's an access code
                long id = CommonUtil.getId(words);
                Media media = null;
                if (id > 0)
                    media = (Media) service.get(Media.class, id);
                // if we got the media, but the code is not valid, set media to null
                if ((media != null) && !media.validCode(words))
                    media = null;
                if (media != null) {
                    List<Media> mediaList = new ArrayList<Media>();
                    mediaList.add(media);
                    model.put("adminSearchWords", words);
                    model.put("mediaList", mediaList);
                } else {
                    // normal search
                    words = MediaUtil.preprocessSearch(words);
                    model.put("adminSearchWords", words);
                    if (!words.endsWith("*"))
                        words += "*";
                    List userList = MediaUtil.searchUser(words, service.getSession());
                    model.put("userList", userList);

                    List mediaList = MediaUtil.searchMedia(words, service.getSession());
                    model.put("mediaList", mediaList);
                }
            }
            model.put("title", "Search Results");
            // get original view name
            if (!"main".equals(getViewName()))
                originalViewName = getViewName();
            setViewName("main");
        } else {
            SearchCriteria.Builder builder = new SearchCriteria.Builder();
            if (StringUtils.isNotBlank(getTheOrderBy()))
                builder.orderBy(getTheOrderBy(), !isTheOrderByDesc());
            SearchCriteria criteria = builder.build();
            Page page = service.pagination(getBeanClass(),
                    pageBean.getP(), pageBean.getS(),
                    criteria);
            page.setRequest(request);
            model.put("pager", page);
            if (StringUtils.isNotBlank(originalViewName))
                setViewName(originalViewName);
        }
        return getModelAndView(model, request);
    }

}
