package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.AccessRecord;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.bean.UploadLocation;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Statistics controller.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 1/03/12
 *         Time: 10:48 AM
 */
public class StatsController extends BaseOperationController {

    private UploadLocation uploadLocation;

    public void setUploadLocation(UploadLocation uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, Object> model = (Map<String, Object>) errors.getModel();
        String cleanUp = request.getParameter("cleanUp");
        if (StringUtils.isNotBlank(cleanUp)) {
            // clean up database
            // remove all original video and audio files
            List<Integer> mediaTypes = new ArrayList<Integer>();
            mediaTypes.add(MediaUtil.MEDIA_TYPE_VIDEO);
            mediaTypes.add(MediaUtil.MEDIA_TYPE_AUDIO);
            SearchCriteria criteria = new SearchCriteria.Builder()
                    .in("mediaType", mediaTypes)
                    .eq("status", MediaUtil.MEDIA_PROCESS_STATUS_FINISHED)
                    .build();
            List<Media> deleted = new ArrayList<Media>();
            @SuppressWarnings("unchecked")
            List<Media> media = (List<Media>) service.search(Media.class, criteria);
            for (Media m : media)
                // if delete successfully, set uploadFileUserName to null
                if (MediaUtil.removeUploadedMediaFiles(uploadLocation, m)) {
                    deleted.add(m);
                    m.setUploadFileUserName(null);
                    service.update(m);
                }
            model.put("deleted", deleted);
        } else {
            int[] mediaTypes = {
                    MediaUtil.MEDIA_TYPE_VIDEO,
                    MediaUtil.MEDIA_TYPE_AUDIO,
                    MediaUtil.MEDIA_TYPE_IMAGE,
                    MediaUtil.MEDIA_TYPE_OTHER_MEDIA,
                    MediaUtil.MEDIA_TYPE_UNKNOWN
            };
            String[] mediaTypeNames = {
                    "Video",
                    "Audio",
                    "Image",
                    "Other",
                    "Unknown"
            };
            List<Map> list = new ArrayList<Map>();
            int total = 0;
            for (int i = 0; i < mediaTypes.length; i++) {
                SearchCriteria criteria = new SearchCriteria.Builder()
                        .eq("mediaType", mediaTypes[i])
                        .build();
                @SuppressWarnings("unchecked")
                List<Media> ll = (List<Media>) service.search(Media.class, criteria);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", mediaTypeNames[i]);
                map.put("num", ll.size());
                total += ll.size();
                list.add(map);
            }
            model.put("list", list);
            model.put("total", total);
            // this feature is only available since 2012
            int startYear = 2012;
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int y = ServletUtil.getParameter(request, "y", 0);
            List<int[]> stats = null;
            if ((y >= startYear) && (y <= currentYear)) {
                // process this year
                stats = processYear(y);
            } else {
                // process current year
                stats = processYear(currentYear);
            }
            if (stats != null)
                model.put("stats", stats);
        }
        return getModelAndView(model, request);
    }


    private List<int[]> processYear(int year) {
        List<int[]> stats = new ArrayList<int[]>();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        int[] actions = {
                MediaUtil.MEDIA_ACTION_UPLOAD,
                MediaUtil.MEDIA_ACTION_UPDATE,
                MediaUtil.MEDIA_ACTION_VIEW,
                MediaUtil.MEDIA_ACTION_DELETE
        };
        int[] total = new int[actions.length];
        for (int i = 0; i < 12; i++) {
            calendar.set(year, i, 1, 0, 0, 0);
            Date firstDayOfThisMonth = calendar.getTime();
            Date firstDayOfNextMonth = DateUtils.addMonths(firstDayOfThisMonth, 1);
            firstDayOfNextMonth = DateUtils.addMilliseconds(firstDayOfNextMonth, -1);
            // if first day of this month is after now, exit from the loop
            if (firstDayOfThisMonth.after(now))
                break;
            int[] results = new int[actions.length];
            for (int a = 0; a < actions.length; a++) {
                // how many actions each month
                SearchCriteria criteria = new SearchCriteria.Builder()
                        .eq("action", actions[a])
                        .between("actionTime", firstDayOfThisMonth, firstDayOfNextMonth)
                        .build();
                @SuppressWarnings("unchecked")
                List<AccessRecord> list = (List<AccessRecord>) service.search(AccessRecord.class, criteria);
                results[a] = list.size();
                total[a] += results[a];
                logger.info("month: " + (i + 1) + " actions: " + results[a]);
            }
            stats.add(results);
        }
        //stats.add(total);
        return stats;
    }

}