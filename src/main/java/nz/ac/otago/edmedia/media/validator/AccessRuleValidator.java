package nz.ac.otago.edmedia.media.validator;

import nz.ac.otago.edmedia.media.bean.AccessRule;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * AccessRule validator.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class AccessRuleValidator implements Validator {

    public boolean supports(Class clazz) {
        return AccessRule.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {

    }
}

