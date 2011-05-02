package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Edit personal profile.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 25/11/2008
 *         Time: 11:16:35
 */
public class MyUserEditController extends BaseFormController {

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {

        User user = (User) command;
        if (StringUtils.isNotBlank(user.getMobile())) {
            String mobile = user.getMobile().trim();
            String newMobile = "";
            // only first char can be '+'
            if (mobile.startsWith("+")) {
                newMobile = "+";
                mobile = mobile.substring(1);
            }
            // then only numbers will be accepted
            for (char c : mobile.toCharArray()) {
                if (CharUtils.isAsciiNumeric(c)) {
                    newMobile += c;
                }
            }
            user.setMobile(newMobile);
        }
        return super.onSubmit(request, response, command, errors);
    }


    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        User user = MediaUtil.getCurrentUser(service, request);
        if (user == null)
            throw new ServletException("Your session is expired.");
        Object obj = service.get(User.class, user.getId());
        return obj;
    }

}
