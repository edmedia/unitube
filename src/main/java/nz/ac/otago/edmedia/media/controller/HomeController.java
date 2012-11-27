package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Home Controller.
 * <p/>
 * Randomly display 5 most visited and 5 most recent media files
 * from 20 media files.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 11/09/2009
 *         Time: 17:00:21
 */
public class HomeController extends BaseOperationController {

    private final static int DISPLAY_NUM = 5;
    private final static int CHOOSE_NUM = 30;

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, Object> model = (Map<String, Object>) errors.getModel();

        {   // most featured videos
            SearchCriteria criteria = new SearchCriteria.Builder()
                    .eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                    .eq("mediaType", MediaUtil.MEDIA_TYPE_VIDEO)
                    .eq("status", MediaUtil.MEDIA_PROCESS_STATUS_FINISHED)
                    .gt("duration", 5 * 60 * 1000) // 5 minutes
                            //.gt("accessTimes", 10) // accessed at least 10 times
                    .build();
            List list = service.search(Media.class, criteria);
            if (!list.isEmpty()) {
                int displayNum = list.size();
                if (displayNum > DISPLAY_NUM)
                    displayNum = DISPLAY_NUM;

                Object[] featured = new Object[displayNum];
                int[] randomArray = CommonUtil.randomArray(list.size(), displayNum);
                for (int i = 0; i < displayNum; i++) {
                    featured[i] = list.get(randomArray[i]);
                }
                model.put("featured", featured);
            }
        }

        {   // most viewed
            SearchCriteria criteria = new SearchCriteria.Builder()
                    .eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                    .eq("status", MediaUtil.MEDIA_PROCESS_STATUS_FINISHED)
                    .orderBy("accessTimes", false)
                    .result(0, CHOOSE_NUM)
                    .build();
            List list = service.search(Media.class, criteria);
            if (!list.isEmpty()) {
                int displayNum = list.size();
                if (displayNum > DISPLAY_NUM)
                    displayNum = DISPLAY_NUM;

                Object[] mostViewed = new Object[displayNum];
                int[] randomArray = CommonUtil.randomArray(list.size(), displayNum);
                for (int i = 0; i < displayNum; i++) {
                    mostViewed[i] = list.get(randomArray[i]);
                }
                model.put("mostViewed", mostViewed);
            }
        }
        {   // most recent
            SearchCriteria criteria = new SearchCriteria.Builder()
                    .eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                    .eq("status", MediaUtil.MEDIA_PROCESS_STATUS_FINISHED)
                    .orderBy("uploadTime", false)
                    .result(0, CHOOSE_NUM)
                    .build();
            List list = service.search(Media.class, criteria);
            if (!list.isEmpty()) {
                int displayNum = list.size();
                if (displayNum > DISPLAY_NUM)
                    displayNum = DISPLAY_NUM;

                Object[] mostRecent = new Object[displayNum];
                int[] randomArray = CommonUtil.randomArray(list.size(), displayNum);
                for (int i = 0; i < displayNum; i++) {
                    mostRecent[i] = list.get(randomArray[i]);
                }
                model.put("mostRecent", mostRecent);
            }
        }
        return getModelAndView(model, request);
    }
}
