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

    private int[] mediaTypes = {
            MediaUtil.MEDIA_TYPE_VIDEO,
            MediaUtil.MEDIA_TYPE_AUDIO,
            MediaUtil.MEDIA_TYPE_IMAGE,
            MediaUtil.MEDIA_TYPE_OTHER_MEDIA,
            MediaUtil.MEDIA_TYPE_UNKNOWN
    };

    private String[] mediaTypeNames = {
            "Video",
            "Audio",
            "Image",
            "Other",
            "Unknown"
    };

    private int[] actions = {
            MediaUtil.MEDIA_ACTION_UPLOAD,
            MediaUtil.MEDIA_ACTION_UPDATE,
            MediaUtil.MEDIA_ACTION_DELETE,
            MediaUtil.MEDIA_ACTION_VIEW
    };

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, Object> model = (Map<String, Object>) errors.getModel();
        model.put("title", "Statistics");
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
            List<Media> deleteList = new ArrayList<Media>();
            @SuppressWarnings("unchecked")
            List<Media> media = (List<Media>) service.search(Media.class, criteria);
            for (Media m : media)
                // if delete successfully, set uploadFileUserName to null
                if (MediaUtil.removeUploadedMediaFiles(uploadLocation, m)) {
                    deleteList.add(m);
                    m.setUploadFileUserName(null);
                    service.update(m);
                }
            model.put("deleteList", deleteList);
        } else {
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
            int m = ServletUtil.getParameter(request, "m", 0);
            List<int[]> stats = null;
            if ((y < startYear) || (y > currentYear))
                y = currentYear;
            model.put("y", y);
            if (m > 0) {
                model.put("m", m);
                // process year and month
                stats = process(y, m);
            } else
                // process year only
                stats = process(y);
            if (stats != null)
                model.put("stats", stats);
        }
        return getModelAndView(model, request);
    }

    /**
     * Process with given year
     *
     * @param year year
     * @return stats for given year
     */
    private List<int[]> process(int year) {
        List<int[]> stats = new ArrayList<int[]>();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        int[] total = new int[actions.length];
        // month is 0 based
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
                logger.debug("month: " + (i + 1) + " action: " + actions[a] + " num: " + results[a]);
            }
            stats.add(results);
        }
        //stats.add(total);
        return stats;
    }

    /**
     * process with given year and month, month is 0 based
     *
     * @param year  year
     * @param month month
     * @return stats for given year and month
     */
    private List<int[]> process(int year, int month) {
        List<int[]> stats = new ArrayList<int[]>();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        int[] total = new int[actions.length];
        // day is 1 based
        for (int i = 1; i <= 31; i++) {
            calendar.set(year, month, i, 0, 0, 0);
            Date startOfTheDay = calendar.getTime();
            Date startOfNextDay = DateUtils.addDays(startOfTheDay, 1);
            startOfNextDay = DateUtils.addMilliseconds(startOfNextDay, -1);
            logger.debug("start of the day: " + startOfTheDay + " start of next day: " + startOfNextDay);
            // if start of the day is after now, exit from the loop
            if (startOfTheDay.after(now) || calendar.get(Calendar.MONTH) > month)
                break;
            int[] results = new int[actions.length];
            for (int a = 0; a < actions.length; a++) {
                // how many actions each month
                SearchCriteria criteria = new SearchCriteria.Builder()
                        .eq("action", actions[a])
                        .between("actionTime", startOfTheDay, startOfNextDay)
                        .build();
                @SuppressWarnings("unchecked")
                List<AccessRecord> list = (List<AccessRecord>) service.search(AccessRecord.class, criteria);
                results[a] = list.size();
                total[a] += results[a];
                logger.debug("day: " + (i + 1) + " action: " + actions[a] + " num: " + results[a]);
            }
            stats.add(results);
        }
        //stats.add(total);
        return stats;
    }
}