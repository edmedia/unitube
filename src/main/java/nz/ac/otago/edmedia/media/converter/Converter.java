package nz.ac.otago.edmedia.media.converter;

import nz.ac.otago.edmedia.media.bean.MediaInfo;

import java.io.File;

/**
 * Converter interface.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 2/07/2008
 *         Time: 16:46:34
 */
public interface Converter {

    /**
     * Is this converter capable to deal with this file?
     *
     * @param input input file
     * @return true if capable
     */
    public boolean isCapable(File input);


    /**
     * Transcode input file to specific outout path
     *
     * @param input      input file
     * @param outputPath output path
     * @return MediaInfo object
     */
    public MediaInfo transcode(File input, File outputPath);

    /**
     * Transcode input file to the same path as input file
     *
     * @param input input file
     * @return MediaInfo object
     */
    public MediaInfo transcode(File input);

    public void setMediaConverter(MediaConverter mediaConverter);

}
