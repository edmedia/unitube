package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.auth.bean.AuthUser;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Create Admin account.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 5/11/2008
 *         Time: 14:04:55
 */
public class CreateAdminController extends BaseOperationController {

    private String fromEmail;

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        // search in database
        User user = MediaUtil.getUser(service, AuthUser.ADMIN_USERNAME, AuthUser.EMBEDDED_WAYF);
        // if not found, create one
        if (user == null) {
            user = new User();
            user.setUserName(AuthUser.ADMIN_USERNAME);
            user.setPassWord(DigestUtils.md5Hex(AuthUser.ADMIN_PASSWORD));
            user.setWayf(AuthUser.EMBEDDED_WAYF);
            user.setFirstName(AuthUser.ADMIN_USERNAME);
            user.setLastName(AuthUser.ADMIN_USERNAME);
            user.setRandomCode(CommonUtil.generateRandomCode());
            if (StringUtils.isNotBlank(fromEmail))
                user.setEmail(fromEmail);
            service.save(user);
        }
        Map model = new HashMap();
        model.put("user", user);
        return getModelAndView(model, request);
    }
}
