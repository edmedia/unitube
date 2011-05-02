package nz.ac.otago.edmedia.media.converter;

import nz.ac.otago.edmedia.media.bean.Images;
import nz.ac.otago.edmedia.media.bean.MediaInfo;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;

/**
 * Office Converter: convert Office file to flash.
 * It involves two steps here:
 * 1. convert office file to PDF file
 * 2. convert PDF file to a bunch of images
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 27/08/2008
 *         Time: 11:20:01
 */
public class OfficeConverter extends AbstractConverter {

    private static final String[] OFFICE_FILE = {
            "doc", // Microsoft Word
            "docx", // Office Open XML
            "rtf", // Rich Text Format
            "odt", // ODF Text Document
            "ott", // ODF Text Document Template
            "sxw", // OpenOffice.org 1.0 Text
            "txt", // Text
            "xls", // Microsoft Excel
            "xlt", // Microsoft Excel Template
            "xlsx", // Office Open XML
            "ods", // ODF Spreadsheet
            "ots", // ODF Spreadsheet Template
            "sxc", // OpenOffice.org 1.0 Spreadsheet
            "htm", // web page
            "html",// web page
            "csv", // Comma-Separated Values
            "tsv", // Tab-Separated Values
            "wpd", // WordPerfect
            "ppt", // Microsoft PowerPoint
            "pps", // Microsoft PowerPoint Show
            "pot", // Microsoft PowerPoint Template
            "pptx", // Office Open XML
            "odp", // ODF Presentation
            "otp", // ODF Presentation Template
            "sxi"  // OpenOffice.org 1.0 Presentation
    };

    public OfficeConverter() {
        capableFileTypes = OFFICE_FILE;
    }

    public MediaInfo transcode(File input, File outputPath) {
        MediaInfo mediaInfo = null;
        // convert office file to pdf
        String pdfFilename = "office.pdf";

        File output = new File(outputPath, pdfFilename);
        if (jodConvert(input, output)) {

            String file1 = "office.png";
            String file2 = "office-0.png";
            File png = new File(outputPath, file1);
            File first = new File(outputPath, file2);
            if (convertPDF2images(output, png)) {
                mediaInfo = new MediaInfo();
                Images images = new Images();
                if (png.exists()) {
                    images.setImage(getImageInfo(png));
                    images.setNumber(1);
                    mediaInfo.setFilename(file1);
                } else if (first.exists()) {
                    images.setImage(getImageInfo(first));
                    Collection list = FileUtils.listFiles(outputPath, new String[]{"png"}, false);
                    images.setNumber(list.size());
                    mediaInfo.setFilename(file2);
                }
                mediaInfo.setImages(images);
                mediaInfo.setDuration("" + images.getNumber());
                String thumbnail = "thumbnail.gif";
                File thumbnailFile = new File(outputPath, thumbnail);
                if (generateThumbnail(output, thumbnailFile)) {
                    if (thumbnailFile.exists())
                        mediaInfo.setThumbnail(thumbnail);
                }
            }
        }
        return mediaInfo;
    }
}
