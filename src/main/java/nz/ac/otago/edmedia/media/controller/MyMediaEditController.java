package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.IVOption;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.converter.AbstractConverter;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Change media title and description.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 1/02/2008
 *         Time: 11:58:54
 */
public class MyMediaEditController extends BaseFormController {

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {

        Media media = (Media) command;
        boolean doConvert = false;
        // if user asked to convert again, set status to "waiting"
        if (request.getParameter("convertAgain") != null) {
            media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_WAITING);
            doConvert = true;
        }
        // if user uploaded a file again, save it and set status to "waiting"
        if ((media.getUploadFile() != null) && (media.getUploadFile().getSize() != 0)) {
            MediaUtil.saveUploaedFile(getUploadLocation(), media);
            media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_WAITING);
            doConvert = true;
            // record re-upload
            MediaUtil.recordUpdate(service, request, media, media.getUser());
        }
        if (doConvert) {
            if (MediaUtil.createTmpFile(getUploadLocation(), media.getAccessCode())) {
                media.setProcessTimes(0);
                service.update(media);
            }
        }
        // if locationCode is empty, set it as same as randomCode
        if (StringUtils.isBlank(media.getLocationCode()))
            media.setLocationCode(media.getRandomCode());

        // change location for private media file
        if (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PRIVATE) {
            String oldLocationCode = media.getLocationCode();
            String newLocationCode = CommonUtil.generateRandomCode();
            if (MediaUtil.moveMediaFile(media, newLocationCode, getUploadLocation())) {
                try {
                    media.setLocationCode(newLocationCode);
                    service.update(media);
                } catch (Exception e) {
                    logger.error("Exception when updating locationCode.", e);
                    // move media files back
                    MediaUtil.moveMediaFile(media, oldLocationCode, getUploadLocation());
                }
            }
        }

        Map model = errors.getModel();
        return getModelAndView(model, request);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map model = super.referenceData(request);
        if (model == null)
            model = new HashMap();
        User user = MediaUtil.getCurrentUser(service, request);
        model.put("user", user);
        Media media = (Media) formBackingObject(request);
        // only create ivOption for image
        if (media.getMediaType() == MediaUtil.MEDIA_TYPE_IMAGE) {
            // get IVOption of this media
            IVOption ivOption = MediaUtil.getIVOption(media, service);
            // if this media has not IVOption, create one
            if (ivOption == null) {
                // default value for ImageViewer option
                // make sure it's consistent with imageViewerHelper.ftl
                ivOption = new IVOption();
                ivOption.setActualWidth(media.getWidth());
                ivOption.setActuralWidthUnit("pixel");
                ivOption.setMinZoom(2);
                ivOption.setMaxZoom(1200);
                ivOption.setDisplayMeasureTool(true);
                ivOption.setOtherCanAnnotate(true);
                ivOption.setMedia(media);
                ivOption.setWhichImageForIV(media.getRealFilename());
                // if image is wider or higher than AbstractConverter.IMAGE_EXTRA_LARGE, use the AbstractConverter.IMAGE_EXTRA_LARGE one for ImageViewer by default
                if ((media.getWidth() > AbstractConverter.IMAGE_EXTRA_LARGE) || (media.getHeight() > AbstractConverter.IMAGE_EXTRA_LARGE))
                    ivOption.setWhichImageForIV("image-e.jpg");
                service.save(ivOption);
            }
            model.put("ivOption", ivOption);
        }
        return model;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        // get object from parent class
        Object obj = super.formBackingObject(request);
        Media media = (Media) obj;
        User user = MediaUtil.getCurrentUser(service, request);
        if (media == null)
            throw new ServletException("Can't find this media file.");
        else if ((media.getUser() == null) || (user == null))
            throw new ServletException("Your session is expired.");
        else if (!media.getUser().getId().equals(user.getId()))
            throw new ServletException("You can not edit other's media file.");
        return obj;
    }

}

