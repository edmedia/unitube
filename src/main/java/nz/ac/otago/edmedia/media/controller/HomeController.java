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
    private final static int CHOOSE_NUM = 20;

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {
        Map model = errors.getModel();

        // list all available media file by accessTimes
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

            Object[] display1 = new Object[displayNum];
            int[] randomArray = CommonUtil.randomArray(list.size(), displayNum);
            for (int i = 0; i < displayNum; i++) {
                display1[i] = list.get(randomArray[i]);
            }
            model.put("mostVisited", display1);
            criteria = new SearchCriteria.Builder()
                    .eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                    .eq("status", MediaUtil.MEDIA_PROCESS_STATUS_FINISHED)
                    .orderBy("uploadTime", false)
                    .result(0, CHOOSE_NUM)
                    .build();
            list = service.search(Media.class, criteria);
            Object[] display2 = new Object[displayNum];
            randomArray = CommonUtil.randomArray(list.size(), displayNum);
            for (int i = 0; i < displayNum; i++) {
                display2[i] = list.get(randomArray[i]);
            }
            model.put("mostRecent", display2);
        }
        return getModelAndView(model, request);
    }
}
