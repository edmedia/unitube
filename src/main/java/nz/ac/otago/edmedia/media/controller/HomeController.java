package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.CommonUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
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

    // update cache every 15 minutes
    public final static long CACHE_UPDATE_INTERVAL = 15 * 60 * 1000;
    private final static int DISPLAY_NUM = 5;
    private final static int CHOOSE_NUM = 30;
    private final static String DATA_FILENAME = "dataHome.data";
    private final static String TEMPLATE_FILENAME = "dataHome.ftl";

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> model = errors.getModel();
        File cacheRoot = new File(getUploadLocation().getUploadDir(), "cache");
        if (!cacheRoot.exists())
            cacheRoot.mkdirs();
        File file = new File(cacheRoot, DATA_FILENAME);
        if (!file.exists() || ((new Date().getTime() - file.lastModified()) > CACHE_UPDATE_INTERVAL)) {
            // generate data
            Map<String, Object> dataModel = new HashMap<String, Object>();
            {   // most featured videos
                SearchCriteria criteria = new SearchCriteria.Builder()
                        .eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                        .eq("mediaType", MediaUtil.MEDIA_TYPE_VIDEO)
                        .eq("status", MediaUtil.MEDIA_PROCESS_STATUS_FINISHED)
                        .gt("duration", 3 * 60 * 1000) // 3 minutes
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
                    dataModel.put("featured", featured);
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
                    dataModel.put("mostViewed", mostViewed);
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
                    dataModel.put("mostRecent", mostRecent);
                }
            }
            dataModel.put("baseUrl", ServletUtil.getContextURL(request));
            dataModel.put("context_url", ServletUtil.getContextURL(request));
            MediaUtil.generateData(this.getServletContext(), dataModel, TEMPLATE_FILENAME, file);
        }
        String content = IOUtils.toString(new FileReader(file));
        model.put("content", content);
        return getModelAndView(model, request);
    }
}
