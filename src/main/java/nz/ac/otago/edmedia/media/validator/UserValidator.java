package nz.ac.otago.edmedia.media.validator;

import nz.ac.otago.edmedia.media.bean.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User validator.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class UserValidator implements Validator {

    public boolean supports(Class clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        // the field after id is required by default
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "field.required", new Object[]{"User Name"}, null);

    }
}

