package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.CommonUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Upload Controller which is used for user to upload media files.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 5/07/2007
 *         Time: 14:46:44
 */
public class UploadController extends BaseFormController {

    private String consumerKey;

    private String consumerSecret;

    private String accessToken;

    private String accessTokenSecret;

    private String proxyHost;

    private int proxyPort;

    private String proxyUser;

    private String proxyPassword;

    private String mailHost;

    private String fromEmail;

    private String smtpUsername;

    private String smtpPassword;

    private int smtpPort;

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {

        Media media = (Media) command;
        MessageSourceAccessor msa = getMessageSourceAccessor();
        // if not upload file, redirect to list
        if ((media.getUploadFile() == null) ||
                (media.getUploadFile().getOriginalFilename() == null) ||
                (media.getUploadFile().getOriginalFilename().trim().length() == 0)) {
            // empty upload
            logger.warn(msa.getMessage("upload.empty"));
            response.sendRedirect("list.do");
            return null;
        }
        User user = MediaUtil.getCurrentUser(service, request);
        media.setUser(user);
        // hide all media files from guest users
        if (user.getIsGuest()) {
            // set access type to hidden for guest
            media.setAccessType(MediaUtil.MEDIA_ACCESS_TYPE_HIDDEN);
        }
        String originalFilename = media.getUploadFile().getOriginalFilename();
        // change extension of filename to lower case
        boolean hasExtension = !"".equals(FilenameUtils.getExtension(originalFilename));
        if (hasExtension)
            originalFilename = FilenameUtils.getBaseName(originalFilename) + "." + FilenameUtils.getExtension(originalFilename).toLowerCase();

        // set title to filename, without extension
        if (hasExtension)
            media.setTitle(FilenameUtils.getBaseName(originalFilename));
        else
            media.setTitle(originalFilename);
        media.setUploadTime(new Date());
        String randomCode = CommonUtil.generateRandomCode();
        media.setRandomCode(randomCode);
        // for private file, set a different locationCode
        if (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PRIVATE)
            media.setLocationCode(CommonUtil.generateRandomCode());
        else
            media.setLocationCode(randomCode);

        MediaUtil.saveUploaedFile(getUploadLocation(), media);

        media.setIsOnOtherServer(false);
        // if uploadOnly is true, don't convert
        if (media.getUploadOnly()) {
            media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_FINISHED);
            media.setMediaType(MediaUtil.MEDIA_TYPE_OTHER_MEDIA);
        } else {
            // we don't convert it at this time, leave it to ConvertTimerTask
            media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_WAITING);
        }
        logger.debug("random code for this media is " + randomCode);
        try {
            service.save(media);

            MediaUtil.recordUpload(service, request, media, user);

            logger.info("User [" + user.getUserName() + "] uploaded [" + originalFilename + "] [m=" + media.getAccessCode() + "] from [" + request.getRemoteAddr() + "].");
            MediaUtil.createTmpFile(getUploadLocation(), media.getAccessCode());
            String subject = msa.getMessage("upload.email.unitube.subject", new String[]{originalFilename, user.getFirstName()});
            String url = ServletUtil.getContextURL(request) + "/view?m=" + media.getAccessCode();
            // have a tweet on UniTube Twitter if media is public, and user chooses to do so
            if (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC) {
                boolean onTwitter = Boolean.parseBoolean(request.getParameter("onTwitter"));
                if (onTwitter)
                    MediaUtil.updateTwitter(consumerKey, consumerSecret, accessToken, accessTokenSecret,
                            proxyHost, proxyPort, proxyUser, proxyPassword,
                            subject + " " + url);
            }
            // send an email to user
            String youSubject = msa.getMessage("upload.email.subject", new String[]{originalFilename});
            String youBody = msa.getMessage("upload.email.body", new String[]{url});
            OtherUtil.sendEmail(mailHost, fromEmail, smtpUsername, smtpPassword, smtpPort,
                    user.getEmail(), youSubject, youBody);
            // send an email to unitube email address
            String body = msa.getMessage("upload.email.unitube.body", new String[]{url});
            OtherUtil.sendEmail(mailHost, fromEmail, smtpUsername, smtpPassword, smtpPort,
                    fromEmail, subject, body);
        } catch (DataAccessException e) {
            logger.error(e);
            throw new ServletException("Exception when saving media", e);
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
        return model;
    }

}
