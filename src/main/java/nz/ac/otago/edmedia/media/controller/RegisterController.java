package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.CommonUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Register a new user.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 25/11/2008
 *         Time: 16:06:32
 */
public class RegisterController extends BaseFormController {

    private String mailHost;

    private String fromEmail;

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {

        User user = (User) command;

        String password = CommonUtil.generateRandomCode();
        user.setPassWord(DigestUtils.md5Hex(password));
        user.setIsGuest(true);
        user.setWayf(AuthUser.EMBEDDED_WAYF);
        user.setRandomCode(CommonUtil.generateRandomCode());
        service.save(user);

        MessageSourceAccessor msa = getMessageSourceAccessor();
        String subject = msa.getMessage("register.email.subject");
        String body = msa.getMessage("register.email.body", new String[]{
                user.getFirstName(), // name
                ServletUtil.getContextURL(request), // url
                user.getUserName(), // username
                password // password
        });
        OtherUtil.sendEmail(mailHost, fromEmail, user.getEmail(), subject, body);

        Map model = errors.getModel();
        return getModelAndView(model, request);
    }
}
