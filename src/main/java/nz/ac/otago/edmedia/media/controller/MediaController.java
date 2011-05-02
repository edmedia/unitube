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
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Media Controller. Display all public media files.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 16/07/2008
 *         Time: 16:19:42
 */
public class MediaController extends BaseListController {

    @SuppressWarnings("unchecked")
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
        Map model = errors.getModel();
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
        return getModelAndView(model, request);
    }

}
