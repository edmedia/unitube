package nz.ac.otago.edmedia.media.exception;

import nz.ac.otago.edmedia.spring.exception.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Display nice message for MaxUploadSizeExceededException.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 18/05/2010
 *         Time: 3:03:31 PM
 */
public class MediaExceptionHandler extends ExceptionHandler {

    @SuppressWarnings("unchecked")
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        if (ex instanceof MaxUploadSizeExceededException) {
            String sizeLimitExceeded = "Our apologies. UniTube can only accept files " +
                    "that are < 1Gb in size.<br/><br/>" +
                    "If you are attempting to upload video, this typically would be " +
                    "around 1.5 hours of video at a resolution of 640x480 pixels.";
            Map model = dataModel(request, handler, ex);
            model.put("message", sizeLimitExceeded);
            return new ModelAndView(getViewName(), model);
        } else
            return super.resolveException(request, response, handler, ex);

    }

}
