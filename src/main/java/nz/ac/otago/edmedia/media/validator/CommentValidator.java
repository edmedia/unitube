package nz.ac.otago.edmedia.media.validator;

import nz.ac.otago.edmedia.media.bean.Comment;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Comment validator.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class CommentValidator implements Validator {

    public boolean supports(Class clazz) {
        return Comment.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {

    }
}

