package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.*;
import nz.ac.otago.edmedia.spring.controller.BaseFormController;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Media FormController.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class MediaFormController extends BaseFormController {

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
            throws Exception {
        Media media = (Media) command;
        // deal with user
        Long userID = media.getUserID();
        if (userID != null) {
            User tmp = (User) service.get(User.class, userID);
            media.setUser(tmp);
        } else {
            media.setUser(null);
        }
        // deal with albumMedia
        Long[] albumMediasID = media.getAlbumMediasID();
        if (albumMediasID != null) {
            Set ids = new HashSet(Arrays.asList(albumMediasID));
            Set toBeRemove = new HashSet();
            if (media.getAlbumMedias() != null)
                for (Iterator it = media.getAlbumMedias().iterator(); it.hasNext();) {
                    AlbumMedia tmp = (AlbumMedia) it.next();
                    if (ids.contains(tmp.getId())) {
                        ids.remove(tmp.getId());
                    } else {
                        toBeRemove.add(tmp);
                    }
                }
            for (Iterator it = toBeRemove.iterator(); it.hasNext();) {
                AlbumMedia tmp = (AlbumMedia) it.next();
                media.removeAlbumMedias(tmp);
            }
            for (Iterator it = ids.iterator(); it.hasNext();) {
                Long id = (Long) it.next();
                AlbumMedia tmp = (AlbumMedia) service.get(AlbumMedia.class, id);
                media.addAlbumMedias(tmp);
            }
        }
        // deal with comment
        Long[] commentsID = media.getCommentsID();
        if (commentsID != null) {
            Set ids = new HashSet(Arrays.asList(commentsID));
            Set toBeRemove = new HashSet();
            if (media.getComments() != null)
                for (Iterator it = media.getComments().iterator(); it.hasNext();) {
                    Comment tmp = (Comment) it.next();
                    if (ids.contains(tmp.getId())) {
                        ids.remove(tmp.getId());
                    } else {
                        toBeRemove.add(tmp);
                    }
                }
            for (Iterator it = toBeRemove.iterator(); it.hasNext();) {
                Comment tmp = (Comment) it.next();
                media.removeComments(tmp);
            }
            for (Iterator it = ids.iterator(); it.hasNext();) {
                Long id = (Long) it.next();
                Comment tmp = (Comment) service.get(Comment.class, id);
                media.addComments(tmp);
            }
        }
        // deal with annotation
        Long[] annotationsID = media.getAnnotationsID();
        if (annotationsID != null) {
            Set ids = new HashSet(Arrays.asList(annotationsID));
            Set toBeRemove = new HashSet();
            if (media.getAnnotations() != null)
                for (Iterator it = media.getAnnotations().iterator(); it.hasNext();) {
                    Annotation tmp = (Annotation) it.next();
                    if (ids.contains(tmp.getId())) {
                        ids.remove(tmp.getId());
                    } else {
                        toBeRemove.add(tmp);
                    }
                }
            for (Iterator it = toBeRemove.iterator(); it.hasNext();) {
                Annotation tmp = (Annotation) it.next();
                media.removeAnnotations(tmp);
            }
            for (Iterator it = ids.iterator(); it.hasNext();) {
                Long id = (Long) it.next();
                Annotation tmp = (Annotation) service.get(Annotation.class, id);
                media.addAnnotations(tmp);
            }
        }
        // deal with accessRule
        Long[] accessRulesID = media.getAccessRulesID();
        if (accessRulesID != null) {
            Set ids = new HashSet(Arrays.asList(accessRulesID));
            Set toBeRemove = new HashSet();
            if (media.getAccessRules() != null)
                for (Iterator it = media.getAccessRules().iterator(); it.hasNext();) {
                    AccessRule tmp = (AccessRule) it.next();
                    if (ids.contains(tmp.getId())) {
                        ids.remove(tmp.getId());
                    } else {
                        toBeRemove.add(tmp);
                    }
                }
            for (Iterator it = toBeRemove.iterator(); it.hasNext();) {
                AccessRule tmp = (AccessRule) it.next();
                media.removeAccessRules(tmp);
            }
            for (Iterator it = ids.iterator(); it.hasNext();) {
                Long id = (Long) it.next();
                AccessRule tmp = (AccessRule) service.get(AccessRule.class, id);
                media.addAccessRules(tmp);
            }
        }
        return super.onSubmit(request, response, command, errors);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map model = super.referenceData(request);
        if (model == null)
            model = new HashMap();
        long id = ServletUtil.getParameter(request, "userID", 0);
        User user = null;
        if (id != 0)
            user = (User) service.get(User.class, id);
        if (user != null)
            model.put("user", user);
        else {
            List userList = service.list(User.class);
            model.put("userList", userList);
        }
        List albumMediasList = service.list(AlbumMedia.class);
        model.put("albumMediasList", albumMediasList);
        List commentsList = service.list(Comment.class);
        model.put("commentsList", commentsList);
        List annotationsList = service.list(Annotation.class);
        model.put("annotationsList", annotationsList);
        List accessRulesList = service.list(AccessRule.class);
        model.put("accessRulesList", accessRulesList);
        return model;
    }

}

