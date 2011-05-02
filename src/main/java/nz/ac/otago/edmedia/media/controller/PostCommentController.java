package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Comment;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Post comment controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 21/10/2009
 *         Time: 11:06:44
 */
public class PostCommentController extends BaseOperationController {

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
            Comment comment = (Comment) command;
            User user = MediaUtil.getCurrentUser(service, request);
            Media media = null;
            String detail = null;
            if ((comment.getMediaID() != null) && (comment.getMediaID() > 0)) {
                media = (Media) service.get(Media.class, comment.getMediaID());
            }
            if ((user != null) && (media != null)) {
                comment.setAuthor(user);
                comment.setMedia(media);
                if ((comment.getCommentID() != null) && (comment.getCommentID() > 0)) {
                    Comment parent = (Comment) service.get(Comment.class, comment.getCommentID());
                    if (parent != null)
                        parent.addChildComments(comment);
                }
                comment.setMsgTime(new Date());
                comment.setCredits(0);
                service.save(comment);
                success = true;
                detail = "" + comment.getId();
            }
            OtherUtil.responseXml(response, "postComment", success, detail);
        }
        return null;
    }

}

