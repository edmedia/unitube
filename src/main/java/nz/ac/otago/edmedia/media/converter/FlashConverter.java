package nz.ac.otago.edmedia.media.converter;

import nz.ac.otago.edmedia.media.bean.Flash;
import nz.ac.otago.edmedia.media.bean.MediaInfo;

import java.io.File;

/**
 * Flash converter.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 27/08/2008
 *         Time: 12:04:01
 */
public class FlashConverter extends AbstractConverter {

    private static final String[] FLASH_FILE = {
            "swf" // flash file
    };

    public FlashConverter() {
        capableFileTypes = FLASH_FILE;
    }

    /**
     * Gets flash information, such as width and height.
     *
     * @param input      input file
     * @param outputPath output path
     * @return MediaInfo object
     */
    public MediaInfo transcode(File input, File outputPath) {
        MediaInfo mediaInfo = null;
        Flash flash = getFlashInfo(input);
        if (flash != null) {
            mediaInfo = new MediaInfo();
            mediaInfo.setFilename(input.getName());
            mediaInfo.setFlash(flash);
        }
        return mediaInfo;

    }
}
