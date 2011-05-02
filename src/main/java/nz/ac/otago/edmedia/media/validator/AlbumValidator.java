package nz.ac.otago.edmedia.media.validator;

import nz.ac.otago.edmedia.media.bean.Album;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Album validator.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class AlbumValidator implements Validator {

    public boolean supports(Class clazz) {
        return Album.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        // the field after id is required by default
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "albumName", "field.required", new Object[]{"Album Name"}, null);

    }
}

