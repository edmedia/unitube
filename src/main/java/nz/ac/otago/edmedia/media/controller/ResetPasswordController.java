package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.CommonUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 21/01/2009
 *         Time: 15:30:15
 */
public class ResetPasswordController extends BaseOperationController {

    private String mailHost;

    private String fromEmail;

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        boolean success = false;
        String q = request.getParameter("u");
        if (q != null) {
            User user = null;
            List list;
            // search username first
            SearchCriteria criteria = new SearchCriteria.Builder()
                    .eq("userName", q)
                    .eq("wayf", AuthUser.EMBEDDED_WAYF)
                    .build();
            list = service.search(User.class, criteria);
            if (!list.isEmpty())
                user = (User) list.get(0);
            if (user == null) {
                // if not found, then search email
                criteria = new SearchCriteria.Builder()
                        .eq("email", q)
                        .eq("wayf", AuthUser.EMBEDDED_WAYF)
                        .build();
                list = service.search(User.class, criteria);
                if (!list.isEmpty())
                    user = (User) list.get(0);
            }
            if (user != null) {
                // generate new password
                String password = CommonUtil.generateRandomCode();
                user.setPassWord(DigestUtils.md5Hex(password));
                service.save(user);

                MessageSourceAccessor msa = getMessageSourceAccessor();
                String subject = msa.getMessage("reset.email.subject");
                String body = msa.getMessage("reset.email.body", new String[]{
                        user.getFirstName(), // name
                        ServletUtil.getContextURL(request), // url
                        user.getUserName(), // username
                        password //password
                });
                success = OtherUtil.sendEmail(mailHost, fromEmail, user.getEmail(), subject, body);
            }
        }
        OtherUtil.responseXml(response, "resetPassword", success);
        return null;
    }
}
