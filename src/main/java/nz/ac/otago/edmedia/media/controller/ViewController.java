package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Comment;
import nz.ac.otago.edmedia.media.bean.IVOption;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * View Controoler.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 26/02/2009
 *         Time: 11:27:15
 */
public class ViewController extends BaseOperationController {

    private String normalView;

    private String loginView;

    public String getNormalView() {
        return normalView;
    }

    public void setNormalView(String normalView) {
        this.normalView = normalView;
    }

    public String getLoginView() {
        return loginView;
    }

    public void setLoginView(String loginView) {
        this.loginView = loginView;
    }

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        if (request.getRequestURI().contains("/myTube/")) {
            // "/myTube/view.do" is only used to make sure user has logged in
            // once they logged in, go to normal view
            String viewName = getNormalView() + "?" + request.getQueryString();
            return new ModelAndView(viewName);
        }
        // get access code from url
        String m = request.getParameter("m");
        // get id from access code
        long id = CommonUtil.getId(m);
        Media media = null;
        if (id > 0)
            media = (Media) service.get(Media.class, id);
        // if we got the media, but the code is not valid, set media to null
        if ((media != null) && !media.validCode(m))
            media = null;
        if (MediaUtil.removeMediaAfter24FromGuest(getUploadLocation(), media, service))
            media = null;
        Map model = errors.getModel();
        model.put("accessDenied", Boolean.FALSE);
        if (media != null) {
            if (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PRIVATE) {
                User user = MediaUtil.getCurrentUser(service, request);
                // if not login yet
                if (user == null) {
                    String viewName = getLoginView() + "?" + request.getQueryString();
                    return new ModelAndView(viewName);
                }
                if (!MediaUtil.canView(media, user)) {
                    media = null;
                    model.put("accessDenied", Boolean.TRUE);
                }
            }

            if (media != null) {
                // set locationCode as same as randomCode if locationCode is empty
                if (StringUtils.isBlank(media.getLocationCode()))
                    media.setLocationCode(media.getRandomCode());
                model.put("obj", media);
                if (media.getMediaType() == MediaUtil.MEDIA_TYPE_IMAGE) {
                    // get ivOption for image file
                    IVOption ivOption = MediaUtil.getIVOption(media, service);
                    if (ivOption != null)
                        model.put("ivOption", ivOption);
                }
                User user = MediaUtil.getCurrentUser(service, request);
                model.put("isOwner", Boolean.FALSE);
                if ((user != null) && user.getId().equals(media.getUser().getId()))
                    model.put("isOwner", Boolean.TRUE);
                if (media.getStatus() == MediaUtil.MEDIA_PROCESS_STATUS_FINISHED) {
                    // increase access times by 1
                    media.setAccessTimes(media.getAccessTimes() + 1);
                    service.update(media);
                }
                SearchCriteria commentCriteria = new SearchCriteria.Builder()
                        .eq("media", media)
                        .isNull("comment")
                        .orderBy("msgTime", false)
                        .result(0, 10) // only first 10 comments
                        .build();
                List commentList = service.search(Comment.class, commentCriteria);

                SearchCriteria allCriteria = new SearchCriteria.Builder()
                        .eq("media", media)
                        .isNull("comment")
                        .orderBy("msgTime", false)
                        .build();
                List allCommentList = service.search(Comment.class, allCriteria);
                if (!commentList.isEmpty())
                    model.put("commentList", commentList);
                model.put("hasMoreComment", Boolean.FALSE);
                if (allCommentList.size() > commentList.size())
                    model.put("hasMoreComment", Boolean.TRUE);
            }
        }
        model.put("title", "View Media File");
        return getModelAndView(model, request);
    }
}
