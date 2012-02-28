package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.util.OtherUtil;
import nz.ac.otago.edmedia.util.CommonUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Post controller: upload to UniTube by http post.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 17/05/2010
 *         Time: 2:19:32 PM
 */
public class PostController extends EmailTwitterController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        Media media = (Media) command;
        MessageSourceAccessor msa = getMessageSourceAccessor();
        boolean success = false;
        String action = "UniTube Post";
        String msg;
        // if not upload file, redirect to list
        if ((media.getUploadFile() == null) ||
                (media.getUploadFile().getOriginalFilename() == null) ||
                (media.getUploadFile().getOriginalFilename().trim().length() == 0)) {
            // empty upload
            msg = msa.getMessage("upload.empty");
            logger.warn(msg);
            OtherUtil.responseXml(response, action, success, msg);
            return null;
        }
        // get user id from user's access code
        long userID = CommonUtil.getId(request.getParameter("userID"));
        User user = (User) service.get(User.class, userID);
        if (user == null) {
            msg = msa.getMessage("user.not.found", new String[]{request.getParameter("userID")});
            logger.warn(msg);
            OtherUtil.responseXml(response, action, success, msg);
            return null;
        }
        media.setUser(user);
        String originalFilename = media.getUploadFile().getOriginalFilename();
        // change extension of filename to lower case
        boolean hasExtension = !"".equals(FilenameUtils.getExtension(originalFilename));
        if (hasExtension)
            originalFilename = FilenameUtils.getBaseName(originalFilename) + "." + FilenameUtils.getExtension(originalFilename).toLowerCase();

        // if title is blank, set title to filename, without extension
        if (StringUtils.isBlank(media.getTitle())) {
            // set title to filename, without extension
            if (hasExtension)
                media.setTitle(FilenameUtils.getBaseName(originalFilename));
            else
                media.setTitle(originalFilename);
        }
        media.setUploadTime(new Date());
        String randomCode = CommonUtil.generateRandomCode();
        media.setRandomCode(randomCode);
        media.setLocationCode(randomCode);

        // save uploaded file
        MediaUtil.saveUploaedFile(getUploadLocation(), media);

        // if uploadOnly is true, don't convert
        if (media.getUploadOnly()) {
            media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_FINISHED);
            media.setMediaType(MediaUtil.MEDIA_TYPE_OTHER_MEDIA);
        } else {
            // we don't convert it at this time, leave it to ConvertTimerTask
            media.setStatus(MediaUtil.MEDIA_PROCESS_STATUS_WAITING);
        }
        // hide all media files from guest users
        if (user.getIsGuest()) {
            // set access type to hidden for guest
            media.setAccessType(MediaUtil.MEDIA_ACCESS_TYPE_HIDDEN);
        }
        // set default value for other fields
        media.setIsOnOtherServer(false);
        media.setAccessType(MediaUtil.MEDIA_ACCESS_TYPE_HIDDEN);
        media.setViaEmail(false);
        media.setViaMMS(false);
        media.setAccessTimes(0);
        media.setProcessTimes(0);
        logger.debug("random code for this media is " + randomCode);
        String url;
        try {
            service.save(media);

            MediaUtil.recordUpload(service, request, media, user);

            url = ServletUtil.getContextURL(request) + "/view?m=" + media.getAccessCode();
            MediaUtil.createTmpFile(getUploadLocation(), media.getAccessCode());
            String subject = msa.getMessage("upload.email.unitube.subject", new String[]{originalFilename, user.getFirstName()});
            // have a tweet on UniTube Twitter if media is public, and user chooses to do so
            if (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC) {
                boolean onTwitter = Boolean.parseBoolean(request.getParameter("onTwitter"));
                if (onTwitter)
                    MediaUtil.updateTwitter(getConsumerKey(), getConsumerSecret(), getAccessToken(), getAccessTokenSecret(),
                            getProxyHost(), getProxyPort(), getProxyUser(), getProxyPassword(),
                            subject + " " + url);
            }
            // send an email to user
            String youSubject = msa.getMessage("upload.email.subject", new String[]{originalFilename});
            String youBody = msa.getMessage("upload.email.body", new String[]{url});
            OtherUtil.sendEmail(getMailHost(), getFromEmail(), getSmtpUsername(), getSmtpPassword(), getSmtpPort(),
                    user.getEmail(), youSubject, youBody);
            // send an email to unitube email address
            String body = msa.getMessage("upload.email.unitube.body", new String[]{url});
            subject = msa.getMessage("upload.email.subject", new String[]{originalFilename, user.getFirstName()});
            OtherUtil.sendEmail(getMailHost(), getFromEmail(), getSmtpUsername(), getSmtpPassword(), getSmtpPort(),
                    getFromEmail(), subject, body);
        } catch (DataAccessException e) {
            logger.error(e);
            throw new ServletException("Exception when saving media", e);
        }
        success = true;
        OtherUtil.responseXml(response, action, success, url);
        return null;
    }

}

