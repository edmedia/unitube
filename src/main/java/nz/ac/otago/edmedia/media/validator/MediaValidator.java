package nz.ac.otago.edmedia.media.validator;

import nz.ac.otago.edmedia.media.bean.Media;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Media validator.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class MediaValidator implements Validator {

    public boolean supports(Class clazz) {
        return Media.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {

    }
}

