package nz.ac.otago.edmedia.media.converter;

import nz.ac.otago.edmedia.media.bean.Images;
import nz.ac.otago.edmedia.media.bean.MediaInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.util.Collection;

/**
 * PDF Converter: convert PDF to a bunch of images.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 27/08/2008
 *         Time: 10:40:41
 */
public class PDFConverter extends AbstractConverter {

    private static final String[] PDF_FILE = {
            "pdf" // PDF file
    };

    public PDFConverter() {
        capableFileTypes = PDF_FILE;
    }

    public MediaInfo transcode(File input, File outputPath) {
        MediaInfo mediaInfo = null;
        String file1 = "pdf.png";
        String file2 = "pdf-0.png";
        File output = new File(outputPath, file1);
        File first = new File(outputPath, file2);
        if (convertPDF2images(input, output)) {
            mediaInfo = new MediaInfo();
            Images images = new Images();
            if (output.exists()) {
                images.setImage(getImageInfo(output));
                images.setNumber(1);
                mediaInfo.setFilename(file1);
            } else if (first.exists()) {
                images.setImage(getImageInfo(first));
                IOFileFilter fileFilter = new RegexFileFilter("^pdf-[0-9]+.png$");
                Collection list = FileUtils.listFiles(outputPath, fileFilter, null);
                images.setNumber(list.size());
                mediaInfo.setFilename(file2);
            }
            mediaInfo.setImages(images);
            mediaInfo.setDuration("" + images.getNumber());
            String thumbnail = "thumbnail.gif";
            File thumbnailFile = new File(outputPath, thumbnail);
            if (generateThumbnail(input, thumbnailFile)) {
                if (thumbnailFile.exists())
                    mediaInfo.setThumbnail(thumbnail);
            }
        }
        return mediaInfo;
    }

}
