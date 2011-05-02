package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.*;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Comment FormController.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class CommentFormController extends BaseFormController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {
        Comment comment = (Comment) command;
        // deal with media
        Long mediaID = comment.getMediaID();
        if (mediaID != null) {
            Media tmp = (Media) service.get(Media.class, mediaID);
            comment.setMedia(tmp);
        } else {
            comment.setMedia(null);
        }
        // deal with user
        Long authorID = comment.getAuthorID();
        if (authorID != null) {
            User tmp = (User) service.get(User.class, authorID);
            comment.setAuthor(tmp);
        } else {
            comment.setAuthor(null);
        }
        // deal with comment
        Long commentID = comment.getCommentID();
        if (commentID != null) {
            Comment tmp = (Comment) service.get(Comment.class, commentID);
            comment.setComment(tmp);
        } else {
            comment.setComment(null);
        }
        // deal with comment
        Long[] childCommentsID = comment.getChildCommentsID();
        if (childCommentsID != null) {
            Set ids = new HashSet(Arrays.asList(childCommentsID));
            Set toBeRemove = new HashSet();
            if (comment.getChildComments() != null)
                for (Iterator it = comment.getChildComments().iterator(); it.hasNext();) {
                    Comment tmp = (Comment) it.next();
                    if (ids.contains(tmp.getId())) {
                        ids.remove(tmp.getId());
                    } else {
                        toBeRemove.add(tmp);
                    }
                }
            for (Iterator it = toBeRemove.iterator(); it.hasNext();) {
                Comment tmp = (Comment) it.next();
                comment.removeChildComments(tmp);
            }
            for (Iterator it = ids.iterator(); it.hasNext();) {
                Long id = (Long) it.next();
                Comment tmp = (Comment) service.get(Comment.class, id);
                comment.addChildComments(tmp);
            }
        }
        return super.onSubmit(request, response, command, errors);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map model = super.referenceData(request);
        if (model == null)
            model = new HashMap();
        List mediaList = service.list(Media.class);
        model.put("mediaList", mediaList);
        List authorList = service.list(User.class);
        model.put("authorList", authorList);
        List commentList = service.list(Comment.class);
        model.put("commentList", commentList);
        List childCommentsList = service.list(Comment.class);
        model.put("childCommentsList", childCommentsList);
        return model;
    }

}

