package nz.ac.otago.edmedia.media.converter;

import nz.ac.otago.edmedia.media.bean.Audio;
import nz.ac.otago.edmedia.media.bean.MediaInfo;
import nz.ac.otago.edmedia.media.bean.Video;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.util.CommandReturn;
import nz.ac.otago.edmedia.util.CommandRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FFmpeg Converter
 * <p/>
 * using FFmpeg to convert audio and video file.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 27/08/2008
 *         Time: 11:28:37
 */
public class FFmpegConverter extends AbstractConverter {

    private final static Logger log = LoggerFactory.getLogger(FFmpegConverter.class);

    private static final int H_264_CODEC = 0;
    private static final int FLASH_MOVIE_CODEC = 1;
    private static final int MP3_CODEC = 2;

    private static final int PREFER_AUDIO_SAMPLE_RATE_HIGH = 48000;
    private static final int AUDIO_SAMPLE_RATE_HIGH = 44100;
    private static final int AUDIO_SAMPLE_RATE_MEDIUM = 22050;
    private static final int AUDIO_SAMPLE_RATE_LOW = 11025;

    private static final String REGEX_DURATION = "Duration:.*";

    private static final String REGEX_VIDEO = "Stream #[0-9].*Video:.*";

    private static final String REGEX_AUDIO = "Stream #[0-9].*Audio:.*";

    private static final String CODEC[] = {
            // "-f mp4 -vcodec libx264 -acodec libfaac -mbd rd -coder ac -cmp satd -subcmp satd ", // H.264
            "-f mp4 -vcodec libx264 -acodec libfaac -vpre hq -crf 23 -threads 0 ", // H.264 for latest FFmpeg (Feb. 2010)
            "-vcodec flv -acodec libmp3lame ",   // Flash Movie
            "-acodec libmp3lame "  // mp3
    };

    private static final String IMAGE_CODEC = "-f image2 -vcodec mjpeg -sameq -vframes 1 ";

    public Pattern patternDuration;

    public Pattern patternVideo;

    public Pattern patternAudio;

    public FFmpegConverter() {
        patternDuration = Pattern.compile(REGEX_DURATION, Pattern.MULTILINE + Pattern.DOTALL);
        patternVideo = Pattern.compile(REGEX_VIDEO);
        patternAudio = Pattern.compile(REGEX_AUDIO);
    }

    public MediaInfo transcode(File input, File outputPath) {
        if (StringUtils.isNotBlank(mediaConverter.getX264Option()))
            CODEC[H_264_CODEC] = mediaConverter.getX264Option();
        MediaInfo mediaInfo = getInfo(input);
        if ((mediaInfo != null)) {
            // if contains video
            if (mediaInfo.getVideo() != null) {
                // get thumbnail picture
                int thumbnailPosition = 4;
                if (MediaUtil.howManySeconds(mediaInfo.getDuration()) < thumbnailPosition) {
                    thumbnailPosition = MediaUtil.howManySeconds(mediaInfo.getDuration());
                    if (thumbnailPosition > 1)
                        thumbnailPosition = 1;
                }
                String thumbnail = getSnapshotFromVideo(input, outputPath, thumbnailPosition);
                mediaInfo.setThumbnail(thumbnail);
                generateThumbnail(new File(outputPath, thumbnail), new File(outputPath, "thumbnail.gif"));

                // only convert video when it's not h264 or flash movie format
                if (false) {

                }
                /*
                else
                if (mediaInfo.getVideo().getFormat().equals("h264") &&
                        ((mediaInfo.getAudio() == null) || mediaInfo.getAudio().getFormat().equals("aac"))) {
                    log.info("\"{}\" is already a H.264 file.", input);
                    mediaInfo.setFilename(input.getName());
                    // even it's already H.264, we have to run qt-faststart to make sure the header is right
                    mediaInfo = qtFaststart(mediaInfo, outputPath, input.getName());
                } //*/
                /**
                 else
                 if (mediaInfo.getVideo().getFormat().equals("flv") || mediaInfo.getVideo().getFormat().equals("vp6f")) {
                 log.info("\"{}\" is already a Flash movie file.", input);
                 mediaInfo.setFilename(input.getName());
                 }    //*/
                else
                if (isOdd(mediaInfo.getVideo().getWidth()) || isOdd(mediaInfo.getVideo().getHeight()))
                    mediaInfo = toVideo(input, mediaInfo, outputPath, FLASH_MOVIE_CODEC);
                else
                    mediaInfo = toVideo(input, mediaInfo, outputPath, H_264_CODEC);

            } else if (mediaInfo.getAudio() != null) {
                /**
                 if (mediaInfo.getAudio().getFormat().equals("mp3")) {
                 log.info("\"{}\" is already a mp3 file.", input);
                 mediaInfo.setFilename(input.getName());
                 } else {
                 mediaInfo = toMP3(input, mediaInfo, outputPath);
                 }  //*/
                // always do the conversion
                mediaInfo = toMP3(input, mediaInfo, outputPath);

            } else {
                log.info("File \"{}\" is not a recognized format.", input);
            }
            if ((mediaInfo.getVideo() != null) || (mediaInfo.getAudio() != null)) {
                // the duration got from original file could be wrong
                // get duration from generated file to make sure it's correct
                if (StringUtils.isNotBlank(mediaInfo.getFilename())) {
                    MediaInfo newInfo = getInfo(new File(outputPath, mediaInfo.getFilename()));
                    if (!newInfo.getDuration().equals(mediaInfo.getDuration())) {
                        newInfo.setFilename(mediaInfo.getFilename());
                        newInfo.setThumbnail(mediaInfo.getThumbnail());
                        mediaInfo = newInfo;
                    }
                }
            }
        } else {
            log.info("File \"{}\" is not a recognized format.", input);
        }
        return mediaInfo;
    }

    /**
     * Gets information for the media file.
     *
     * @param input media file
     * @return information about this media
     */
    private MediaInfo getInfo(File input) {
        MediaInfo mediaInfo = null;
        StopWatch sw = new StopWatch();
        sw.start();
        String ffmpeg = mediaConverter.getFfmpeg();
        StringBuilder command = new StringBuilder(ffmpeg);
        command.append(" -i ");
        command.append(input.getAbsolutePath());
        command.append("");
        CommandReturn commandReturn = CommandRunner.run(command.toString());
        log.info("get info for file \"{}\"", input);
        log.debug("{}", commandReturn);

        String output = commandReturn.toString();
        if (!StringUtils.containsIgnoreCase(output, "Unknown format") &&
                //!StringUtils.containsIgnoreCase(output, "could not find codec parameters") &&
                !StringUtils.containsIgnoreCase(output, "Unsupported video codec")) {

            Matcher m = patternDuration.matcher(output);
            if (m.find()) {
                mediaInfo = new MediaInfo();

                output = output.substring(m.start(), m.end());
                // example: Duration: 00:00:22.2, start: 0.232178, bitrate: 383719 kb/s
                String sDuration = StringUtils.substringBetween(output, "Duration:", ",");
                if (sDuration != null)
                    mediaInfo.setDuration(sDuration);
                String sBitrate = StringUtils.substringBetween(output, "bitrate:", "kb/s");
                int bitrate = 0;
                if (sBitrate != null)
                    try {
                        bitrate = Integer.parseInt(sBitrate.trim());
                    } catch (NumberFormatException e) {
                        // ignore
                    }
                mediaInfo.setBitrate(bitrate);

                // example: Stream #0.1(und): Video: h264, yuv420p, 240x180 [PAR 0:1 DAR 0:1], 12.50 tb(r)
                // example: Stream #0.0[0x1e0]: Video: mpeg2video, yuv420p, 720x576 [PAR 16:15 DAR 4:3], 9548 kb/s, 25.00 tb(r)
                // example from a rmvb file: Stream #0.0: Video: RV40 / 0x30345652, 624x352 [PAR 0:1 DAR 0:1], 450 kb/s, 12.00 tb(r)
                Matcher m1 = patternVideo.matcher(output);
                if (m1.find()) {

                    String videoLine = output.substring(m1.start(), m1.end());
                    if (videoLine != null) {
                        Video video = getVideo(videoLine);
                        mediaInfo.setVideo(video);
                        mediaInfo.setWidth(video.getWidth());
                        mediaInfo.setHeight(video.getHeight());
                    } else
                        mediaInfo.setVideo(null);
                }
                // example: Stream #0.0(und): Audio: mpeg4aac, 48000 Hz, stereo
                // example: Stream #0.1[0x80]: Audio: liba52, 48000 Hz, stereo, 256 kb/s
                Matcher m2 = patternAudio.matcher(output);
                if (m2.find()) {
                    String audioLine = output.substring(m2.start(), m2.end());
                    if (audioLine != null)
                        mediaInfo.setAudio(getAudio(audioLine));
                    else
                        mediaInfo.setAudio(null);
                }
            }

        }
        sw.stop();
        log.debug("{}", mediaInfo);
        log.debug("getInfo run time {}", sw);
        return mediaInfo;
    }

    /**
     * Gets video information for given movie.
     *
     * @param line example: "Stream #0.1(und): Video: h264, yuv420p, 240x180 [PAR 0:1 DAR 0:1], 12.50 tb(r)"
     *             example from a rmvb file
     *             ": RV40 / 0x30345652, 624x352 [PAR 0:1 DAR 0:1], 450 kb/s, 12.00 tb(r)"
     *             Another example without rate
     *             ": svq3, yuv420p, 480x360, 25.00 tb(r)"
     * @return video information
     */
    private Video getVideo(String line) {
        String format = StringUtils.substringBetween(line, "Video:", ",").trim();
        String partLine = line.substring(line.indexOf(","));
        String sWidth = StringUtils.substringBetween(partLine, ",", "x");
        while ((sWidth != null) && (sWidth.indexOf(",") != -1))
            sWidth = StringUtils.substringAfter(sWidth, ",");
        int width = 0;
        if (sWidth != null)
            try {
                width = Integer.parseInt(sWidth.trim());
            } catch (NumberFormatException e) {
                // ignore
            }
        String sHeight = StringUtils.substringBetween(partLine, "x", ",");
        if ((sHeight != null) && (sHeight.indexOf("[") != -1))
            sHeight = StringUtils.substringBefore(sHeight, "[");
        int height = 0;
        if (sHeight != null)
            try {
                height = Integer.parseInt(sHeight.trim());
            } catch (NumberFormatException e) {
                // ignore
            }
        String sRate = StringUtils.substringBetween(line, ",", "kb/s");
        while ((sRate != null) && (sRate.indexOf(",") != -1))
            sRate = StringUtils.substringAfter(sRate, ",");
        int rate = 0;
        if (sRate != null)
            try {
                rate = Integer.parseInt(sRate.trim());
            } catch (NumberFormatException e) {
                // ignore
            }
        String sFreq = StringUtils.substringBetween(line, ",", "tb");
        while ((sFreq != null) && (sFreq.indexOf(",") != -1))
            sFreq = StringUtils.substringAfter(sFreq, ",");
        float freq = 0;
        if (sFreq != null)
            try {
                freq = Float.parseFloat(sFreq);
            } catch (NumberFormatException e) {
                // ignore
            }
        Video video = new Video();
        video.setFormat(format);
        video.setWidth(width);
        video.setHeight(height);
        video.setRate(rate);
        video.setFreq(freq);
        return video;
    }

    /**
     * Gets audio information for given movie.
     *
     * @param line example: "Stream #0.1(eng): Audio: libfaad, 16000 Hz, mono"
     *             Another example without rate
     *             ": adpcm_ima_qt, 22050 Hz, mono"
     * @return audio information
     */
    private Audio getAudio(String line) {
        String format = StringUtils.substringBetween(line, "Audio:", ",").trim();
        String sFreq = StringUtils.substringBetween(line, ",", "Hz");
        int freq = 0;
        if (sFreq != null)
            try {
                freq = Integer.parseInt(sFreq.trim());
            } catch (NumberFormatException e) {
                // ignore
            }
        boolean stereo = false;
        String sStereo = StringUtils.substringBetween(line, "Hz,", ",");
        if (sStereo == null)
            sStereo = StringUtils.substringAfter(line, "Hz,");
        if ((sStereo != null) && (sStereo.indexOf("stereo") != -1))
            stereo = true;

        // sometimes, there is no rate information, such as files from ITS seminar
        String sRate = StringUtils.substringBetween(line, ",", "kb/s");
        while ((sRate != null) && (sRate.indexOf(",") != -1))
            sRate = StringUtils.substringAfter(sRate, ",");
        int rate = 0;
        if (sRate != null)
            try {
                rate = Integer.parseInt(sRate.trim());
            } catch (NumberFormatException e) {
                // ignore
            }
        Audio audio = new Audio();
        audio.setFormat(format);
        audio.setFreq(freq);
        audio.setStereo(stereo);
        audio.setRate(rate);
        return audio;
    }

    /**
     * Gets snapshot image for given video file.
     *
     * @param input            media file
     * @param outputPath       where to put output file
     * @param snapshotPosition where to get thumbnail, in seconds
     * @return filename of thumbnail without path
     */
    private String getSnapshotFromVideo(File input, File outputPath, int snapshotPosition) {
        StopWatch sw = new StopWatch();
        sw.start();
        String ffmpeg = mediaConverter.getFfmpeg();
        StringBuilder command = new StringBuilder(ffmpeg);
        command.append(" ");
        command.append("-i ");
        command.append(input.getAbsolutePath());
        command.append(" ");
        command.append(IMAGE_CODEC);
        command.append(" ");
        // position to get picture
        if (snapshotPosition > 0) {
            command.append("-ss ");
            command.append(snapshotPosition);
            command.append(" ");
        } else
            // default is at 3 seconc
            command.append("-ss 3 ");
        String filename = "snapshot.jpg";
        File output = new File(outputPath, filename);
        command.append(output.getAbsolutePath());
        CommandReturn commandReturn = CommandRunner.run(command.toString());
        log.info("get thumbnail \"{}\" for file \"{}\"", output, input);
        log.debug("{}", commandReturn);
        sw.stop();
        log.debug("get thumbnail run time {}", sw);
        return filename;
    }

    /**
     * Converts input file to Flash Video or H.264 Video.
     *
     * @param input      input file
     * @param mediaInfo  mediaInfo object
     * @param outputPath output path
     * @param codec      which codec to use, Flash Movie or H.264 Movie
     * @return returns mediaInfo object
     */
    private MediaInfo toVideo(File input, MediaInfo mediaInfo, File outputPath, int codec) {
        StopWatch sw = new StopWatch();
        sw.start();
        String ffmpeg = mediaConverter.getFfmpeg();
        StringBuilder command = new StringBuilder(ffmpeg);
        command.append(" ");
        command.append("-i ");
        command.append(input.getAbsolutePath());
        command.append(" ");
        command.append(CODEC[codec]);
        command.append(" ");

        // video parameters here

        // video rate
        // command.append("-vb 500k ");
        // set title
        //command.append("-title \"title here\" ");
        //command.append("-author \"HEDC edmedia\" ");
        //command.append("-copyright \"HEDC edmedia\" ");

        // audio parameters here
        //**
        // command.append("-acodec libmp3lame ");

        // options for audio

        // flv only supports sample rate from (44100, 22050, 11025)
        if (codec == FLASH_MOVIE_CODEC) {
            if (mediaInfo.getAudio() != null) {
                if (mediaInfo.getAudio().getFreq() >= 44100)
                    command.append("-ar 44100 ");
                else if (mediaInfo.getAudio().getFreq() >= 22050)
                    command.append("-ar 22050 ");
                else
                    command.append("-ar 11025 ");
            }
        }

        audioSetup(command, mediaInfo, true);

        String filename = "movie";
        if (input.getName().contains(filename))
            filename = "video";
        if (codec == H_264_CODEC)
            filename += ".mp4";
        else if (codec == FLASH_MOVIE_CODEC)
            filename += ".flv";
        File output = new File(outputPath, filename);
        command.append(output.getAbsolutePath());

        CommandReturn commandReturn = CommandRunner.run(command.toString());
        // check commandReturn exit status
        //if(commandReturn.exitStatus)
        log.debug("{}", commandReturn);
        sw.stop();
        log.info("convert run time {}", sw);
        log.debug("Original file size: {} generated file size: {} 100:{}", new Object[]{input.length(), output.length(), (output.length() * 100 / input.length())});
        if (output.exists() && (output.length() > 0)) {
            mediaInfo.setFilename(filename);
            // only run qt-faststart when using H.264 codec
            if (codec == H_264_CODEC) {
                mediaInfo = qtFaststart(mediaInfo, outputPath, filename);
            }
        }
        return mediaInfo;
    }

    private MediaInfo qtFaststart(MediaInfo mediaInfo, File outputPath, String filename) {
        File output = new File(outputPath, filename);
        String qt_faststart = mediaConverter.getQt_faststart();
        StringBuilder qtCommand = new StringBuilder(qt_faststart);
        qtCommand.append(" ");
        qtCommand.append(output.getAbsolutePath());
        qtCommand.append(" ");
        String newFilename = "video.mp4";
        if (newFilename.equals(filename))
            newFilename = "movie.mp4";
        File newFile = new File(outputPath, newFilename);
        qtCommand.append(newFile.getAbsolutePath());
        CommandReturn qtReturn = CommandRunner.run(qtCommand.toString());
        if (newFile.exists() && output.exists() && (newFile.length() == output.length())) {
            mediaInfo.setFilename(newFilename);
            // remove old file
            if (!FileUtils.deleteQuietly(output))
                log.warn("Can't delete output \"{}\"", output);
            log.debug("{}", qtReturn);
        }
        return mediaInfo;
    }

    private MediaInfo toMP3(File input, MediaInfo mediaInfo, File outputPath) {
        StopWatch sw = new StopWatch();
        sw.start();
        String ffmpeg = mediaConverter.getFfmpeg();
        StringBuilder command = new StringBuilder(ffmpeg);
        command.append(" ");
        command.append("-i ");
        command.append(input.getAbsolutePath());
        command.append(" ");

        command.append(CODEC[MP3_CODEC]);

        audioSetup(command, mediaInfo, true);

        String filename = "audio.mp3";
        if (input.getName().contains(filename))
            filename = "voice.mp3";
        File output = new File(outputPath, filename);
        command.append(output.getAbsolutePath());

        CommandReturn commandReturn = CommandRunner.run(command.toString());
        // check commandReturn exit status
        //if(commandReturn.exitStatus)
        log.debug("{}", commandReturn);
        sw.stop();
        log.info("convert run time {}", sw);
        log.debug("Original file size: {} generated file size: {} 100:{}", new Object[]{input.length(), output.length(), (output.length() * 100 / input.length())});
        mediaInfo.setFilename(filename);
        return mediaInfo;
    }

    private void audioSetup(StringBuilder command, MediaInfo mediaInfo, boolean setFrequence) {
        // options for audio
        if (mediaInfo.getAudio() != null) {
            if (setFrequence) {
                if (mediaInfo.getAudio().getFreq() >= AUDIO_SAMPLE_RATE_HIGH)
                    command.append("-ar " + PREFER_AUDIO_SAMPLE_RATE_HIGH + " ");
                else if (mediaInfo.getAudio().getFreq() >= AUDIO_SAMPLE_RATE_MEDIUM)
                    command.append("-ar " + AUDIO_SAMPLE_RATE_MEDIUM + " ");
                else
                    command.append("-ar " + AUDIO_SAMPLE_RATE_LOW + " ");
            }
            if ((mediaInfo.getAudio().getRate() >= 128) || (mediaInfo.getAudio().getFreq() >= AUDIO_SAMPLE_RATE_HIGH))
                command.append("-ab 128k ");
            else if ((mediaInfo.getAudio().getRate() >= 64) || (mediaInfo.getAudio().getFreq() >= AUDIO_SAMPLE_RATE_MEDIUM))
                command.append("-ab 64k ");
            else
                command.append("-ab 32k ");
        }
    }

    private boolean isOdd(int num) {
        return ((num / 2) * 2 != num);
    }

    public static void main(String args[]) {

        FFmpegConverter convert = new FFmpegConverter();

        System.out.println(convert.isOdd(13));

        System.out.println(convert.isOdd(2));


    }
}
