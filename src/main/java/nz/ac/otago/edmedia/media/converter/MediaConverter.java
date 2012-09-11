package nz.ac.otago.edmedia.media.converter;

import nz.ac.otago.edmedia.media.bean.MediaInfo;
import nz.ac.otago.edmedia.util.CommandReturn;
import nz.ac.otago.edmedia.util.CommandRunner;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Media Converter, the entrance class which will use different converter to convert media file according their file type.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 27/08/2008
 *         Time: 11:17:54
 */
public class MediaConverter {

    private final static Logger log = LoggerFactory.getLogger(MediaConverter.class);

    private static final String FFMPEG_COMMAND = "ffmpeg";

    private static final String QT_FASTSTART_COMMAND = "qt-faststart";

    private static final String IMAGE_MAGICK_CONVERT_COMMAND = "convert";

    private static final String IMAGE_MAGICK_IDENTIFY_COMMAND = "identify";


    private static final String SWFDUMP_COMMAND = "swfdump";

    private static final String PDF2SWF_COMMAND = "pdf2swf";

    private static final String SWFCOMBINE_COMMAND = "swfcombine";


    private String ffmpeg;

    private String qt_faststart;

    private String convert;

    private String identify;

    private String swfdump;

    private String pdf2swf;
    private String swfcombine;

    private String antivirus;

    private int virusStatus;

    private String x264Option;

    public String getFfmpeg() {
        if (StringUtils.isNotBlank(ffmpeg))
            return ffmpeg;
        else
            return FFMPEG_COMMAND;
    }

    public void setFfmpeg(String ffmpeg) {
        this.ffmpeg = ffmpeg;
    }

    public String getQt_faststart() {
        if (StringUtils.isNotBlank(qt_faststart))
            return qt_faststart;
        else
            return QT_FASTSTART_COMMAND;
    }

    public void setQt_faststart(String qt_faststart) {
        this.qt_faststart = qt_faststart;
    }

    public String getConvert() {
        if (StringUtils.isNotBlank(convert))
            return convert;
        else
            return IMAGE_MAGICK_CONVERT_COMMAND;
    }

    public void setConvert(String convert) {
        this.convert = convert;
    }

    public String getIdentify() {
        if (StringUtils.isNotBlank(identify))
            return identify;
        else
            return IMAGE_MAGICK_IDENTIFY_COMMAND;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getSwfdump() {
        if (StringUtils.isNotBlank(swfdump))
            return swfdump;
        else
            return SWFDUMP_COMMAND;
    }

    public void setSwfdump(String swfdump) {
        this.swfdump = swfdump;
    }

    public String getPdf2swf() {
        if (StringUtils.isNotBlank(pdf2swf))
            return pdf2swf;
        else
            return PDF2SWF_COMMAND;
    }

    public void setPdf2swf(String pdf2swf) {
        this.pdf2swf = pdf2swf;
    }

    public String getSwfcombine() {
        if (StringUtils.isNotBlank(swfcombine))
            return swfcombine;
        else
            return SWFCOMBINE_COMMAND;
    }

    public void setSwfcombine(String swfcombine) {
        this.swfcombine = swfcombine;
    }

    public String getAntivirus() {
        return antivirus;
    }

    public void setAntivirus(String antivirus) {
        this.antivirus = antivirus;
    }

    public int getVirusStatus() {
        return virusStatus;
    }

    public void setVirusStatus(int virusStatus) {
        this.virusStatus = virusStatus;
    }

    public String getX264Option() {
        return x264Option;
    }

    public void setX264Option(String x264Option) {
        this.x264Option = x264Option;
    }

    /**
     * All converters here. The order is very important, because if there's more than one converter
     * capable of dealing with one file format, the first one will take action.
     */
    private static final Converter[] converters = {
            //new PresentationConverter(),
            new OfficeConverter(),
            new PDFConverter(),
            new FlashConverter(),
            new ImageConverter(),
            new FFmpegConverter()
    };

    public MediaConverter() {
        for(Converter converter: converters) {
            converter.setMediaConverter(this);
        }
    }


    /**
     * Converts a file to a format which can be used on web page.
     *
     * @param input      input media
     * @param outputPath output path
     * @return returns MediaInfo object
     */
    public MediaInfo transcode(File input, File outputPath) {

        MediaInfo mediaInfo = null;
        if (input.exists()) {
            for (Converter converter : converters) {
                if (converter.isCapable(input)) {
                    mediaInfo = converter.transcode(input, outputPath);
                    break;
                }
            }
        } else {
            log.info("File \"{}\" does not exist.", input);
        }
        return mediaInfo;
    }

    /**
     * Converts input file to other video format.
     *
     * @param input       input file
     * @param outputPath  output path
     * @param otherFormat other format, file extension, such as mpg
     * @return returns file name of other video format
     */
    public String toOtherFormat(File input, File outputPath, String otherFormat) {
        StopWatch sw = new StopWatch();
        sw.start();
        StringBuilder command = new StringBuilder();
        command.append(ffmpeg);
        command.append(" ");
        command.append("-i ");
        command.append(input.getAbsolutePath());
        command.append(" ");
        // same quality
        command.append("-sameq ");
        command.append("-r 20 ");

        String filename = "other." + otherFormat;
        if (input.getName().contains(filename))
            filename = "format." + otherFormat;
        File output = new File(outputPath, filename);
        command.append(output.getAbsolutePath());

        CommandReturn commandReturn = CommandRunner.run(command.toString());
        // check commandReturn exit status
        //if(commandReturn.exitStatus)
        log.debug("{}", commandReturn);
        sw.stop();
        log.info("convert run time {}", sw);
        log.debug("Original file size: {} generated file size: {} 100:{}", new Object[]{input.length(), output.length(), (output.length() * 100 / input.length())});
        return filename;
    }


    public static void main(String args[]) {

        MediaConverter mc = new MediaConverter();

        File input = new File("/home/richard/app/www/movie/videos/its-seminar-oct07.mp4");
        File outputPath = new File("/home/richard/temp/");
        mc.transcode(input, outputPath);

    }


}

