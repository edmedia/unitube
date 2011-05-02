package nz.ac.otago.edmedia.media.converter;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import nz.ac.otago.edmedia.media.bean.Flash;
import nz.ac.otago.edmedia.media.bean.Image;
import nz.ac.otago.edmedia.media.bean.MediaInfo;
import nz.ac.otago.edmedia.util.CommandReturn;
import nz.ac.otago.edmedia.util.CommandRunner;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.ConnectException;

/**
 * Abstract Converter
 * <p/>
 * some useful methods for sub class.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 27/08/2008
 *         Time: 10:43:08
 */
abstract public class AbstractConverter implements Converter {

    private final Logger log = LoggerFactory.getLogger(AbstractConverter.class);

    protected MediaConverter mediaConverter;

    /**
     * match all file types, and must be the first one in capable file type array
     */
    private static final String CAPABLE_FOR_ALL_FILE_TYPES = "*";

    protected static final int[] IMAGE_THUMBNAIL = {60, 50};

    protected static final int[] IMAGE_SMALL = {240, 180};

    protected static final int IMAGE_MEDIUM = 800;

    protected static final int IMAGE_LARGE = 1024;

    protected static final int IMAGE_VERY_LARGE = 2048;

    public static final int IMAGE_EXTRA_LARGE = 4096;


    /**
     * an array of all capable file types.
     */
    protected String[] capableFileTypes = {
            CAPABLE_FOR_ALL_FILE_TYPES
    };

    /**
     * Check if this converter is capable to convert given file.
     * Returns true only when given file exists, and firt capable file type is *
     * or one of capable file types  matches given file.
     *
     * @param input input file
     * @return true only when
     */
    public boolean isCapable(File input) {
        return input.exists() &&
                (capableFileTypes[0].equals(CAPABLE_FOR_ALL_FILE_TYPES) ||
                        FilenameUtils.isExtension(input.getAbsolutePath().toLowerCase(), capableFileTypes));
    }

    public MediaInfo transcode(File input) {
        if (input.exists() && input.isFile())
            return transcode(input, new File(input.getParent()));
        else
            return null;
    }

    public void setMediaConverter(MediaConverter mediaConverter) {
        this.mediaConverter = mediaConverter;
    }

    /**
     * Returns information for flash file, such as width, height.
     *
     * @param file flash file
     * @return flash object
     */
    protected Flash getFlashInfo(File file) {
        Flash flash = null;
        if (file.exists()) {
            log.info("get flash info for file \"{}\"", file.getAbsolutePath());
            StringBuilder command = new StringBuilder();
            command.append(mediaConverter.getSwfdump());
            command.append(" -X -Y ");
            command.append(file.getAbsolutePath());
            CommandReturn commandReturn = CommandRunner.run(command.toString());
            log.debug("{}", commandReturn);

            String output = commandReturn.toString();
            // try 10 times
            for (int i = 0; i < 10; i++) {
                if ((output == null) || (output.indexOf("-X") == -1)) {
                    commandReturn = CommandRunner.run(command.toString());
                    log.debug("{}", commandReturn);
                    output = commandReturn.toString();
                }
            }
            if ((output != null) && (output.indexOf("-X") != -1)) {

                flash = new Flash();
                // -X 720 -Y 540
                String sWidth = StringUtils.substringBetween(output, "-X", "-Y");
                if (sWidth != null) {
                    int width = Integer.parseInt(sWidth.trim());
                    flash.setWidth(width);
                }
                String sHeight = StringUtils.substringBetween(output, "-Y", "\n");
                if (sHeight != null) {
                    int height = Integer.parseInt(sHeight.trim());
                    flash.setHeight(height);
                }
            }
            log.debug("{}", flash);
        }
        return flash;
    }

    /**
     * Converts PDF file to flash file using external command pdf2swf.
     *
     * @param input  input file
     * @param output output file
     * @return true if successful, otherwize false
     */
    public boolean convertPDF2swf(File input, File output) {
        boolean result = false;
        if (input.exists()) {
            StopWatch sw = new StopWatch();
            sw.start();
            String pdf2swf = mediaConverter.getPdf2swf();
            StringBuilder command = new StringBuilder();
            command.append(pdf2swf);
            command.append(" ");
            // Link default viewer to the pdf (/usr/share/swftools/swfs/default_viewer.swf)
            // Link default preloader the pdf (/usr/share/swftools/swfs/default_loader.swf)
            command.append("-bl ");
            command.append(input.getAbsolutePath());
            command.append(" ");
            command.append("-o ");
            command.append(output.getAbsolutePath());

            CommandReturn commandReturn = CommandRunner.run(command.toString());
            log.info("{} for file \"{}\"", pdf2swf, input.getAbsolutePath());
            log.debug(commandReturn.toString());
            sw.stop();
            log.debug("{} run time {}", pdf2swf, sw);
            if (output.exists() && (output.length() > 0))
                result = true;
        }
        return result;
    }

    /**
     * Uses ImageMagick get thumbnail image from first page of a PDF file or image file
     *
     * @param input  input file
     * @param output output file
     * @return true if successful, otherwize false
     */
    public boolean generateThumbnail(File input, File output) {
        return imageMagickConvert(input, output, IMAGE_THUMBNAIL[0], IMAGE_THUMBNAIL[1]);
    }

    /**
     * Uses ImageMagick to convert image file.
     *
     * @param input  input file
     * @param output output file
     * @return true if successful, otherwize false
     */
    public boolean imageMagickConvert(File input, File output) {
        return imageMagickConvert(input, output, 0, 0);
    }

    /**
     * Uses ImageMagick to convert and resize image file or PDF file.
     *
     * @param input        input file
     * @param output       output file
     * @param resizeWidth  resized width
     * @param resizeHeight resized height
     * @return true if successful, otherwize false
     */
    public boolean imageMagickConvert(File input, File output, int resizeWidth, int resizeHeight) {
        boolean result = false;
        if (input.exists()) {
            StopWatch sw = new StopWatch();
            sw.start();
            StringBuilder command = new StringBuilder();
            String convert = mediaConverter.getConvert();
            command.append(convert);
            command.append(" ");
            command.append(input.getAbsolutePath());
            command.append("[0] ");
            if ((resizeWidth > 0) && (resizeHeight > 0)) {
                command.append("-resize ");
                command.append(resizeWidth);
                command.append("x");
                command.append(resizeHeight);
                command.append("> ");
            } else if (resizeWidth > 0) {
                command.append("-resize ");
                command.append(resizeWidth);
                command.append("> ");
            }
            command.append(output.getAbsolutePath());

            CommandReturn commandReturn = CommandRunner.run(command.toString());
            log.info("{} for file \"{}\"", convert, input.getAbsolutePath());
            log.debug(commandReturn.toString());
            sw.stop();
            log.debug("{} run time {}", convert, sw);
            if (output.exists() && (output.length() > 0))
                result = true;
        }
        return result;
    }

    public Image getImageInfo(File file) {
        // example output from "identify test.jpg"
        // test.jpg JPEG 612x792 612x792+0+0 PseudoClass 256c 8-bit 4.40039kb
        // plasma.jpg JPEG 3382x4096 3382x4096+0+0 8-bit PseudoClass 256c 10.4mb
        Image image = null;
        if (file.exists()) {
            // try 10 times
            for (int i = 0; i < 10; i++) {
                log.info("get image info for file \"{}\"", file.getAbsolutePath());
                StringBuilder command = new StringBuilder();
                String identify = mediaConverter.getIdentify();
                command.append(identify);
                command.append(" ");
                command.append(file.getAbsolutePath());
                CommandReturn commandReturn = CommandRunner.run(command.toString());
                log.debug("{}", commandReturn);

                String output = commandReturn.toString();
                if ((output != null) && (output.indexOf("x") != -1)) {
                    image = new Image();
                    int index = output.indexOf(file.getName()) + file.getName().length();
                    String sWidth = StringUtils.substringBetween(output, file.getName(), "x");
                    while ((sWidth != null) && (sWidth.indexOf(" ") != -1)) {
                        sWidth = sWidth.substring(sWidth.indexOf(" ")).trim();
                    }
                    if (sWidth != null) {
                        int width = Integer.parseInt(sWidth.trim());
                        image.setWidth(width);
                    }
                    output = output.substring(index);
                    String sHeight = StringUtils.substringBetween(output, "x", " ");
                    if (sHeight != null) {
                        int height = Integer.parseInt(sHeight.trim());
                        image.setHeight(height);
                    }
                    log.debug("{}", image);
                    if ((image.getWidth() == 0) || (image.getHeight() == 0))
                        image = null;
                }
                if (image != null)
                    break;
            }
        }
        return image;
    }

    /**
     * Calls JODConverter to convert input file to output.
     *
     * @param input  input file
     * @param output output file
     * @return true if sucess, false otherwise
     */
    public boolean jodConvert(File input, File output) {
        boolean result = false;
        if (input.exists()) {
            StopWatch sw = new StopWatch();
            sw.start();
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
            try {
                connection.connect();
                DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
                converter.convert(input, output);
                connection.disconnect();
                sw.stop();
                log.info("JODConverter run time {}", sw);
                if (output.exists() && (output.length() > 0))
                    result = true;
            } catch (ConnectException e) {
                log.error("{}", e);
            }
        }
        return result;
    }

    public boolean addNavigationToFlash(File input, File container, File output) {
        boolean result = false;
        if (input.exists()) {
            StopWatch sw = new StopWatch();
            sw.start();
            StringBuilder command = new StringBuilder();
            String swfcombine = mediaConverter.getSwfcombine();
            command.append(swfcombine);
            command.append(" -z -o");
            command.append(output.getAbsolutePath());
            command.append(container.getAbsolutePath());
            command.append(" container=");
            command.append(input.getAbsolutePath());
            CommandReturn commandReturn = CommandRunner.run(command.toString());
            log.info("{} for file \"{}\"", swfcombine, input.getAbsolutePath());
            log.debug("{}", commandReturn);
            sw.stop();
            log.debug("{} run time {}", swfcombine, sw);
            if (output.exists() && (output.length() > 0))
                result = true;
        }
        return result;
    }

    /**
     * Converts PDF file to a sequence of image files using external command convert.
     * <p/>
     * convert -density 144 your.pdf output.png
     * default density is 72dpi
     *
     * @param input  input file
     * @param output output file
     * @param density density
     * @return true if successful, otherwize false
     */
    public boolean convertPDF2images(File input, File output, int density) {
        boolean result = false;
        if (input.exists()) {
            StopWatch sw = new StopWatch();
            sw.start();
            String convert = mediaConverter.getConvert();
            StringBuilder command = new StringBuilder();
            command.append(convert);
            command.append(" -density ");
            command.append(density);
            command.append(" ");
            command.append(input.getAbsolutePath());
            command.append(" ");
            command.append(output.getAbsolutePath());

            CommandReturn commandReturn = CommandRunner.run(command.toString());
            log.info("{} for file \"{}\"", convert, input.getAbsolutePath());
            log.debug(commandReturn.toString());
            sw.stop();
            log.debug("{} run time {}", convert, sw);
            if (output.exists() && (output.length() > 0))
                result = true;
            else {
                // check pdf-0.png
                String filename = FilenameUtils.getBaseName(output.getAbsolutePath()) + "-0." +
                        FilenameUtils.getExtension(output.getAbsolutePath());
                File zero = new File(output.getParent(), filename);
                if (zero.exists() && (zero.length() > 0))
                    result = true;
            }
        }
        return result;
    }

    /**
     * Converts PDF file to a sequence of image files using external command convert.
     * <p/>
     * using 108dpi as default density
     *
     * @param input  input file
     * @param output output file
     * @return true if successful, otherwize false
     */
    public boolean convertPDF2images(File input, File output) {
        return convertPDF2images(input, output, 108);
    }

}
