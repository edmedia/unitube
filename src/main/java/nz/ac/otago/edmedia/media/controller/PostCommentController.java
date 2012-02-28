package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Comment;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
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

    private String mailHost;

    private String fromEmail;

    private String smtpUsername;

    private String smtpPassword;

    private int smtpPort;

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

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

                MessageSourceAccessor msa = getMessageSourceAccessor();
                String url = ServletUtil.getContextURL(request) + "/view?m=" + media.getAccessCode() + "#comment_" + comment.getId();
                String subject = msa.getMessage("new.comment.email.subject", new String[]{user.getFirstName()});
                String body = msa.getMessage("new.comment.email.body", new String[]{comment.getMsg(), url});
                OtherUtil.sendEmail(mailHost, fromEmail, smtpUsername, smtpPassword, smtpPort,
                        media.getUser().getEmail(), subject, body);

                success = true;
                detail = "" + comment.getId();
            }
            OtherUtil.responseXml(response, "postComment", success, detail);
        }
        return null;
    }

}

