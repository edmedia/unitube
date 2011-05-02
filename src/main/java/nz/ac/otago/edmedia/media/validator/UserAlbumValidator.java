package nz.ac.otago.edmedia.media.validator;

import nz.ac.otago.edmedia.media.bean.UserAlbum;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * UserAlbum validator.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class UserAlbumValidator implements Validator {

    public boolean supports(Class clazz) {
        return UserAlbum.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {

    }
}

