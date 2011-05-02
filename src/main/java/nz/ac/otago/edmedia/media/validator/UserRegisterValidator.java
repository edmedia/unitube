package nz.ac.otago.edmedia.media.validator;

import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.spring.service.BaseService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 23/12/2008
 *         Time: 10:52:19
 */
public class UserRegisterValidator implements Validator {

    private BaseService service;

    public void setService(BaseService service) {
        this.service = service;
    }

    public boolean supports(Class clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        // the field after id is required by default
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "field.required", new Object[]{"User Name"}, null);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required", new Object[]{"First Name"}, null);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.required", new Object[]{"Last Name"}, null);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", new Object[]{"Email Address"}, null);
        User user = (User) target;
        // check username is not in DB
        List list = service.search(User.class, "userName", user.getUserName());
        if (!list.isEmpty()) {
            errors.rejectValue("userName", "userName.exists", new Object[]{user.getUserName()}, null);
        }
        // check email is not in DB
        list = service.search(User.class, "email", user.getEmail());
        if (!list.isEmpty()) {
            errors.rejectValue("email", "email.exists", new Object[]{user.getEmail()}, null);
        }


    }
}
