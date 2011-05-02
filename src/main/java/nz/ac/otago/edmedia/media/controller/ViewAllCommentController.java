package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Comment;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * View all comment controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 4/11/2009
 *         Time: 11:15:20
 */
public class ViewAllCommentController extends BaseOperationController {

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        // get access code from url
        String m = request.getParameter("m");
        // get id from access code
        long id = CommonUtil.getId(m);
        Media media = null;
        if (id > 0)
            media = (Media) service.get(Media.class, id);
        // if we got the media, but the code is not valid, set media to null
        if ((media != null) && !media.validCode(m))
            media = null;
        if (MediaUtil.removeMediaAfter24FromGuest(getUploadLocation(), media, service))
            media = null;
        Map model = errors.getModel();
        if (media != null) {
            model.put("obj", media);
            SearchCriteria criteria = new SearchCriteria.Builder()
                    .eq("media", media)
                    .isNull("comment")
                    .orderBy("msgTime", false)
                    .build();
            List commentList = service.search(Comment.class, criteria);
            if (!commentList.isEmpty())
                model.put("commentList", commentList);
        }
        model.put("title", "View All Comments");
        return getModelAndView(model, request);
    }
}

