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
import java.util.Random;

/**
 * Register a new user.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 25/11/2008
 *         Time: 16:06:32
 */
public class RegisterController extends EmailController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        boolean success = false;
        String detail;
        User user = (User) command;

        MessageSourceAccessor msa = getMessageSourceAccessor();

        // search email first
        SearchCriteria criteria = new SearchCriteria.Builder()
                .eq("email", user.getEmail())
                .eq("wayf", AuthUser.EMBEDDED_WAYF)
                .build();
        @SuppressWarnings("unchecked")
        List<User> list = (List<User>) service.search(User.class, criteria);
        // if not empty, this email is in use
        if (!list.isEmpty()) {
            detail = msa.getMessage("register.email.in.use");
        } else {
            String userName = user.getUserName();
            // set username to first name if empty
            if (StringUtils.isBlank(userName))
                userName = user.getFirstName().toLowerCase();
            Random random = new Random();
            String extra = "";
            do {
                userName += extra;
                criteria = new SearchCriteria.Builder()
                        .eq("userName", userName)
                        .eq("wayf", AuthUser.EMBEDDED_WAYF)
                        .build();
                list = (List<User>) service.search(User.class, criteria);
                extra += random.nextInt(10);
            } while (!list.isEmpty());
            user.setUserName(userName);
            String password = CommonUtil.generateRandomCode();
            user.setPassWord(DigestUtils.md5Hex(password));
            user.setIsGuest(true);
            user.setWayf(AuthUser.EMBEDDED_WAYF);
            user.setRandomCode(CommonUtil.generateRandomCode());
            service.save(user);

            String subject = msa.getMessage("register.email.subject");
            String body = msa.getMessage("register.email.body", new String[]{
                    user.getFirstName(), // name
                    ServletUtil.getContextURL(request), // url
                    user.getUserName(), // username
                    password // password
            });
            OtherUtil.sendEmail(getMailHost(), getFromEmail(), getSmtpUsername(), getSmtpPassword(), getSmtpPort(),
                    user.getEmail(), subject, body);
            success = true;
            detail = msa.getMessage("register.success");
        }
        OtherUtil.responseXml(response, "register", success, detail);
        return null;
    }
}
