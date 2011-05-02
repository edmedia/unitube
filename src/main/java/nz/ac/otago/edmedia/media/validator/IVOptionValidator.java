package nz.ac.otago.edmedia.media.validator;

import nz.ac.otago.edmedia.media.bean.IVOption;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * IVOption validator.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class IVOptionValidator implements Validator {

    public boolean supports(Class clazz) {
        return IVOption.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {

    }
}

