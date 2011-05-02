package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.auth.util.AuthUtil;
import nz.ac.otago.edmedia.media.bean.Comment;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Delete Comment.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 30/10/2009
 *         Time: 09:36:38
 */
public class DeleteCommentController extends BaseOperationController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        String m = request.getParameter("m");
        if (StringUtils.isNotBlank(m)) {
            response.sendRedirect("../view?m=" + m);
        } else {
            boolean success = false;
            String detail = "You are not allowed to delete this comment";
            long id = ServletUtil.getParameter(request, "id", 0L);
            User user = MediaUtil.getCurrentUser(service, request);
            Comment comment = null;
            if (id > 0)
                comment = (Comment) service.get(Comment.class, id);
            if (comment != null) {
                boolean canDelete = false;
                // if current user is the owner of the comment
                if (user.getId().equals(comment.getAuthor().getId()))
                    canDelete = true;
                // if current user is the owner of the media
                if (user.getId().equals(comment.getMedia().getUser().getId()))
                    canDelete = true;
                AuthUser authUser = AuthUtil.getAuthUser(request);
                // if current user is instructor
                if ((authUser != null) && authUser.getIsInstructor())
                    canDelete = true;
                if (canDelete) {
                    service.delete(comment);
                    success = true;
                    detail = null;
                }
            }
            OtherUtil.responseXml(response, "deleteComment", success, detail);
        }
        return null;
    }

}
