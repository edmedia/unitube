package nz.ac.otago.edmedia.media.controller;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedOutput;
import nz.ac.otago.edmedia.media.bean.Album;
import nz.ac.otago.edmedia.media.bean.AlbumMedia;
import nz.ac.otago.edmedia.media.bean.Media;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import nz.ac.otago.edmedia.util.CommonUtil;
import nz.ac.otago.edmedia.util.ServletUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * Create a syndication feed.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 21/08/2008
 *         Time: 13:58:03
 */
public class SyndFeedController extends BaseOperationController {

    private String contextUrl = null;

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        SyndFeed feed = new SyndFeedImpl();
        // default format is rss 2.0
        boolean feedTypeAtom = false;
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String pName = (String) enu.nextElement();
            if (pName.toLowerCase().startsWith("rss")) {
                feedTypeAtom = false;
                break;
            } else if (pName.toLowerCase().startsWith("atom")) {
                feedTypeAtom = true;
                break;
            }
        }
        if (feedTypeAtom) {
            feed.setFeedType("atom_1.0");
            response.setContentType("application/atom+xml");
        } else {
            feed.setFeedType("rss_2.0");
            response.setContentType("application/rss+xml");
        }
        contextUrl = ServletUtil.getContextURL(request);

        String topic = request.getParameter("topic");
        if ("album".equals(topic)) {
            albumFeed(feed, request);
        } else if ("media".equals(topic)) {
            mediaFeed(feed, request);
        }

        if (feed.getTitle() == null) {
            feed.setTitle("Nothing available");
            feed.setDescription("Can not find anything");
            feed.setLink(contextUrl);
        }

        SyndFeedOutput output = new SyndFeedOutput();
        response.setCharacterEncoding("utf-8");
        output.output(feed, response.getWriter());
        return null;
    }

    @SuppressWarnings("unchecked")
    private void albumFeed(SyndFeed feed, HttpServletRequest request) {
        // get access code from url
        String a = request.getParameter("a");
        Album album = null;
        if (a != null)
            album = (Album) service.get(Album.class, CommonUtil.getId(a));
        if ((album != null) && album.validCode(a)) {
            feed.setTitle(album.getAlbumName());
            if (album.getDescription() != null)
                feed.setDescription(album.getDescription());
            else
                feed.setDescription("");
            feed.setLink(contextUrl + "/album?a=" + a);

            List entries = new ArrayList();

            for (AlbumMedia albumMedia : album.getAlbumMedias()) {
                Media media = albumMedia.getMedia();
                // display private media in private album
                // hide private media in public album
                if (((album.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_HIDDEN) && (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_HIDDEN)) ||
                        (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)) {
                    SyndEntry entry = getEntry(media);
                    entries.add(entry);
                }
            }
            Collections.sort(entries, new EntryComparator());
            Collections.reverse(entries);
            feed.setEntries(entries);
        }
    }

    @SuppressWarnings("unchecked")
    private void mediaFeed(SyndFeed feed, HttpServletRequest request) {
        // get unique user code from url
        String u = request.getParameter("u");
        User user = null;
        if (u != null)
            user = (User) service.get(User.class, CommonUtil.getId(u));
        // if we got the user, but the code is not valid, set user to null
        if ((user != null) && !user.validCode(u))
            user = null;
        if (user != null) {
            feed.setTitle("Feed for " + user.getFirstName() + " " + user.getLastName());
            feed.setDescription("");
            feed.setLink(contextUrl + "/media.do?u=" + u);
        } else {
            feed.setTitle("Feed for UniTube");
            feed.setDescription("");
            feed.setLink(contextUrl + "/media.do");
        }
        List entries = new ArrayList();
        SearchCriteria.Builder builder = new SearchCriteria.Builder();
        builder = builder.eq("accessType", MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC)
                .eq("status", MediaUtil.MEDIA_PROCESS_STATUS_FINISHED)
                .orderBy("uploadTime", false)
                .result(0, 20);
        if (user != null)
            builder = builder.eq("user", user);
        SearchCriteria criteria = builder.build();
        List<Media> records = (List<Media>) service.search(Media.class, criteria);
        for (Media media : records) {
            if (media.getAccessType() == MediaUtil.MEDIA_ACCESS_TYPE_PUBLIC) {
                SyndEntry entry = getEntry(media);
                entries.add(entry);
            }
        }
        feed.setEntries(entries);
    }

    @SuppressWarnings("unchecked")
    private SyndEntry getEntry(Media media) {
        SyndEntry entry = new SyndEntryImpl();
        entry.setTitle(media.getTitle());
        entry.setLink(contextUrl + "/view?m=" + media.getAccessCode());
        entry.setPublishedDate(media.getUploadTime());
        entry.setUpdatedDate(media.getUploadTime());
        entry.setAuthor(media.getUser().getUserName());
        if (media.getMediaType() == MediaUtil.MEDIA_TYPE_AUDIO ||
                media.getMediaType() == MediaUtil.MEDIA_TYPE_VIDEO ||
                media.getUploadFileUserName().endsWith(".pdf")) {
            SyndEnclosure enclosure = new SyndEnclosureImpl();
            String url = contextUrl + "/file.do?m=" + media.getAccessCode();
            // get media directory
            File mediaDir = MediaUtil.getMediaDirectory(getUploadLocation(), media);
            String filename;
            if (StringUtils.isNotBlank(media.getUploadFileUserName()) && media.getUploadFileUserName().endsWith(".pdf")) {
                filename = media.getUploadFileUserName();
                url += "&name=" + media.getUploadFileUserName();
            } else
                filename = media.getRealFilename();
            File file = new File(mediaDir, filename);
            enclosure.setUrl(url);
            enclosure.setLength(file.length());
            if (media.getMediaType() == MediaUtil.MEDIA_TYPE_AUDIO)
                enclosure.setType("audio/mpeg");
            else if (media.getMediaType() == MediaUtil.MEDIA_TYPE_VIDEO)
                enclosure.setType("video/mp4");
            else if (filename.endsWith(".pdf"))
                enclosure.setType("application/pdf");
            List list = new ArrayList();
            list.add(enclosure);
            entry.setEnclosures(list);
        }
        if (StringUtils.isNotBlank(media.getDescription())) {
            SyndContent description = new SyndContentImpl();
            description.setType("text/html");
            description.setValue(media.getDescription());
            entry.setDescription(description);
        }
        return entry;
    }

}

class EntryComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        if ((o1 instanceof SyndEntry) && (o2 instanceof SyndEntry)) {
            SyndEntry se1 = (SyndEntry) o1;
            SyndEntry se2 = (SyndEntry) o2;
            if (se1.getPublishedDate().before(se2.getPublishedDate()))
                return -1;
            if (se1.getPublishedDate().after(se2.getPublishedDate()))
                return 1;
        }
        return 0;
    }
}

