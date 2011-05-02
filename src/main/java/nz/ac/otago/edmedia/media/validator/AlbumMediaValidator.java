package nz.ac.otago.edmedia.media.validator;

import nz.ac.otago.edmedia.media.bean.AlbumMedia;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * AlbumMedia validator.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class AlbumMediaValidator implements Validator {

    public boolean supports(Class clazz) {
        return AlbumMedia.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {

    }
}

