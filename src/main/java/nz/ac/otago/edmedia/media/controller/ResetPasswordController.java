package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.CommonUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Reset Password Controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 21/01/2009
 *         Time: 15:30:15
 */
public class ResetPasswordController extends EmailController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        boolean success = false;
        String detail = null;
        String q = request.getParameter("u");
        if (q != null) {
            User user = null;
            List<User> list;
            // search username first
            SearchCriteria criteria = new SearchCriteria.Builder()
                    .eq("userName", q)
                    .eq("wayf", AuthUser.EMBEDDED_WAYF)
                    .build();
            list = (List<User>) service.search(User.class, criteria);
            if (!list.isEmpty())
                user = list.get(0);
            if (user == null) {
                // if not found, then search email
                criteria = new SearchCriteria.Builder()
                        .eq("email", q)
                        .eq("wayf", AuthUser.EMBEDDED_WAYF)
                        .build();
                list = (List<User>) service.search(User.class, criteria);
                if (!list.isEmpty())
                    user = list.get(0);
            }
            MessageSourceAccessor msa = getMessageSourceAccessor();
            if (user != null) {
                if (StringUtils.isNotBlank(user.getEmail())) {
                    // generate new password
                    String password = CommonUtil.generateRandomCode();
                    user.setPassWord(DigestUtils.md5Hex(password));
                    service.save(user);

                    String subject = msa.getMessage("reset.password.email.subject");
                    String body = msa.getMessage("reset.password.email.body", new String[]{
                            user.getFirstName(), // name
                            ServletUtil.getContextURL(request), // url
                            user.getUserName(), // username
                            password //password
                    });
                    OtherUtil.sendEmail(getMailHost(), getFromEmail(), getSmtpUsername(), getSmtpPassword(), getSmtpPort(),
                            user.getEmail(), subject, body);
                    detail = msa.getMessage("reset.password.success");
                    success = true;
                } else
                    detail = msa.getMessage("reset.password.user.has.no.email");
            } else {
                detail = msa.getMessage("reset.password.user.not.found", new String[]{q});
            }
        }

        OtherUtil.responseXml(response, "resetPassword", success, detail);
        return null;
    }
}
