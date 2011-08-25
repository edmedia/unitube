package nz.ac.otago.edmedia.media.validator;

import nz.ac.otago.edmedia.media.bean.AVP;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * AVP validator.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 25/08/11
 *         Time: 2:18 PM
 */
public class AVPValidator implements Validator {

    public boolean supports(Class clazz) {
        return AVP.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        AVP avp = (AVP) target;
        // Audio/Video One and Presentation are required
        if (avp.getAv1ID() == 0)
            errors.rejectValue("av1ID", "field.required", new Object[]{"Audio/Video One"}, "");
        if (avp.getPresentationID() == 0)
            errors.rejectValue("presentationID", "field.required", new Object[]{"Presentation"}, "");
    }
}
