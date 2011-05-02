package nz.ac.otago.edmedia.media.validator;

import nz.ac.otago.edmedia.media.bean.Annotation;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Annotation validator.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class AnnotationValidator implements Validator {

    public boolean supports(Class clazz) {
        return Annotation.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        // the field after id is required by default
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "annotName", "field.required", new Object[]{"Annotation Name"}, null);

    }
}

