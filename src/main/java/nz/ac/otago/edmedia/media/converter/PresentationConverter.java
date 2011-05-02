package nz.ac.otago.edmedia.media.converter;

import nz.ac.otago.edmedia.media.bean.Flash;
import nz.ac.otago.edmedia.media.bean.MediaInfo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Presentation Converter
 * <p/>
 * convert presentation file to flash, such as Microsoft PowerPoint file
 * and OpenOffice Presentation file.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 27/08/2008
 *         Time: 11:19:32
 */
public class PresentationConverter extends AbstractConverter {

    private final static Logger log = LoggerFactory.getLogger(PresentationConverter.class);

    private static final String[] PRESENTATION_FILE = {
            "ppt", // Microsoft PowerPoint
            "pps", // Microsoft PowerPoint Show
            "pot", // Microsoft PowerPoint Template
            //"pptx", // Office Open XML
            "odp", // ODF Presentation
            "otp", // ODF Presentation Template
            "sxi"  // OpenOffice.org 1.0 Presentation
    };

    public PresentationConverter() {
        capableFileTypes = PRESENTATION_FILE;
    }

    public MediaInfo transcode(File input, File outputPath) {

        MediaInfo mediaInfo = null;
        String filename = "presentation.swf";
        File output = new File(outputPath, filename);
        if (jodConvert(input, output)) {
            Flash flash = getFlashInfo(output);
            mediaInfo = new MediaInfo();
            mediaInfo.setFilename(filename);
            mediaInfo.setFlash(flash);

            // generate thumbnail
            // convert presentation file to pdf, then get thumbnail from pdf
            String pdfFilename = "presentation.pdf";
            File pdfOutput = new File(outputPath, pdfFilename);
            if (jodConvert(input, pdfOutput)) {
                String thumbnail = "thumbnail.gif";
                File thumbnailFile = new File(outputPath, thumbnail);
                if (generateThumbnail(pdfOutput, thumbnailFile)) {
                    if (thumbnailFile.exists())
                        mediaInfo.setThumbnail(thumbnail);
                }
                // delete PDF file after thumbnail
                if (!FileUtils.deleteQuietly(pdfOutput))
                    log.warn("Can't delete PDF \"{}\"", pdfOutput);
            }
        }
        return mediaInfo;
    }

}
