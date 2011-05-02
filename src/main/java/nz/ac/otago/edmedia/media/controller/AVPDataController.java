package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * Audio/Video Presentation data.
 *
 * @author: Richard Zeng (richard.zeng@otago.ac.nz)
 * Date: Mar 24, 2011
 * Time: 11:19:27 AM
 */
public class AVPDataController extends BaseOperationController {

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors)
            throws Exception {

        // get xml from url
        String xml = request.getParameter("xml");
        // default file name
        String filename = "avpTest.xml";
        if (StringUtils.isNotBlank(xml))
            filename = xml;

        filename = getServletContext().getRealPath(filename);
        File file = new File(filename);
        if (file.exists()) {
            response.setContentType("application/xml");
            IOUtils.copy(new FileInputStream(file), response.getWriter());
            response.getWriter().close();
        }
        return null;
    }
}
