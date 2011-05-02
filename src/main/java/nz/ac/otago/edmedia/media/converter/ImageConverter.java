package nz.ac.otago.edmedia.media.converter;

import nz.ac.otago.edmedia.media.bean.Image;
import nz.ac.otago.edmedia.media.bean.MediaInfo;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * Image Converter
 * <p/>
 * Deal with image file using ImageMagick
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 27/08/2008
 *         Time: 12:04:56
 */
public class ImageConverter extends AbstractConverter {

    private static final String[] IMAGE_FILE = {
            //A* RAW       rw+   Raw alpha samples
            "ai",//AI  PDF       rw-   Adobe Illustrator CS2
            "art",//ART* ART       rw-   PFS: 1st Publisher Clip Art
            "arw",//ARW  DNG       r--   Sony Alpha Raw Image Format
            //AVI* AVI       r--   Microsoft Audio/Visual Interleaved
            "avs",//AVS* AVS       rw+   AVS X image
            //B* RAW       rw+   Raw blue samples
            "bmp",//BMP* BMP       rw-   Microsoft Windows bitmap image
            "bmp2",//BMP2* BMP       -w-   Microsoft Windows bitmap image v2
            "bmp3",//BMP3* BMP       -w-   Microsoft Windows bitmap image v3
            //C* RAW       rw+   Raw cyan samples
            //CAPTION* CAPTION   r--   Image caption
            "cin",//CIN* CIN       rw+   Cineon Image File
            "cip",//CIP* CIP       -w-   Cisco IP phone image format
            //CLIP* CLIP      -w+   Image Clip Mask
            //CMYK* CMYK      rw+   Raw cyan, magenta, yellow, and black samples
            //CMYKA* CMYK      rw+   Raw cyan, magenta, yellow, black, and opacity samples
            "cr2",//CR2  DNG       r--   Canon Digital Camera Raw Image Format
            "crw",//CRW  DNG       r--   Canon Digital Camera Raw Image Format
            "cur",//CUR* CUR       rw-   Microsoft icon
            "cut",//CUT* CUT       r--   DR Halo
            "dcm",//DCM* DCM       r--   Digital Imaging and Communications in Medicine image
            "dcr",//DCR  DNG       r--   Kodak Digital Camera Raw Image File
            "dcx",//DCX* PCX       rw+   ZSoft IBM PC multi-page Paintbrush
            //DFONT* TTF       r--   Multi-face font package (Freetype 2.3.5)
            "djvu",//DJVU* DJVU      r--   D�j� vu
            "dng",//DNG  DNG       r--   Digital Negative
            //DOT  DOT       r--   Graphviz
            "dps",//DPS  DPS       ---   Display Postscript Interpreter
            "dpx",//DPX* DPX       rw+   SMPTE 268M-2003 (DPX 2.0)
            //EPDF  PDF       rw-   Encapsulated Portable Document Format
            "epi",//EPI  PS        rw-   Encapsulated PostScript Interchange format
            "eps",//EPS  PS        rw-   Encapsulated PostScript
            //EPS2* PS2       -w-   Level II Encapsulated PostScript
            //EPS3* PS3       -w+   Level III Encapsulated PostScript
            //EPSF  PS        rw-   Encapsulated PostScript
            //EPSI  PS        rw-   Encapsulated PostScript Interchange format
            "ept",//EPT  EPT       rw-   Encapsulated PostScript with TIFF preview
            //EPT2  EPT       rw-   Encapsulated PostScript Level II with TIFF preview
            //EPT3  EPT       rw+   Encapsulated PostScript Level III with TIFF preview
            "exr",//EXR* EXR       rw+   High Dynamic-range (HDR)
            //FAX* FAX       rw+   Group 3 FAX
            //FITS* FITS      rw-   Flexible Image Transport System
            //FRACTAL* PLASMA    r--   Plasma fractal image
            //FTS* FTS       rw-   Flexible Image Transport System
            //G* RAW       rw+   Raw green samples
            //G3* FAX       rw-   Group 3 FAX
            "gif",//GIF* GIF       rw+   CompuServe graphics interchange format
            //GIF87* GIF       rw-   CompuServe graphics interchange format (version 87a)
            //GRADIENT* GRADIENT  r--   Gradual passing from one shade to another
            //GRAY* GRAY      rw+   Raw gray samples
            //HISTOGRAM* HISTOGRAM -w-   Histogram of the image
            //HTM* HTML      -w-   Hypertext Markup Language and a client-side image map
            //HTML* HTML      -w-   Hypertext Markup Language and a client-side image map
            "icb",//      ICB* TGA       rw+   Truevision Targa image
            "ico",//ICO* ICON      rw+   Microsoft icon
            //ICON* ICON      rw-   Microsoft icon
            //INFO  INFO      -w+   The image format and characteristics
            "ipl",//IPL* IPL       rw+   IPL Image Sequence
            "jng",//JNG* PNG       rw-   JPEG Network Graphics
            "jp2",//JP2* JP2       rw-   JPEG-2000 File Format Syntax
            "jpc",//JPC* JPC       rw-   JPEG-2000 Code Stream Syntax
            "jpeg",//JPEG* JPEG      rw-   Joint Photographic Experts Group JFIF format (62)
            "jpg",//JPG* JPEG      rw-   Joint Photographic Experts Group JFIF format
            "jpx",//JPX* JPX       rw-   JPEG-2000 File Format Syntax
            //K* RAW       rw+   Raw black samples
            "k25",//K25  DNG       r--   Kodak Digital Camera Raw Image Format
            "kdc",//KDC  DNG       r--   Kodak Digital Camera Raw Image Format
            //LABEL* LABEL     r--   Image label
            //  M* RAW       rw+   Raw magenta samples
            //M2V  MPEG      rw+   MPEG Video Stream
            //MAP* MAP       rw-   Colormap intensities and indices
            "mat",//MAT* MAT       rw+   MATLAB image format
            //MATTE* MATTE     -w+   MATTE format
            "miff",//MIFF* MIFF      rw+   Magick Image File Format
            "mng",//MNG* PNG       rw+   Multiple-image Network Graphics (libpng 1.2.15beta5)
            "mpc",//MPC* MPC       rw+   Magick Persistent Cache image format
            //MPEG  MPEG      rw+   MPEG Video Stream
            //MPG  MPEG      rw+   MPEG Video Stream
            "mrw",//MRW  DNG       r--   Sony (Minolta) Raw Image File
            //MSL* MSL       rw+   Magick Scripting Language
            //MSVG* SVG       rw+   ImageMagick's own SVG internal renderer
            //MTV* MTV       rw+   MTV Raytracing image format
            //MVG* MVG       rw-   Magick Vector Graphics
            //NEF  DNG       r--   Nikon Digital SLR Camera Raw Image File
            //NULL* NULL      rw-   Constant image of uniform color
            //O* RAW       rw+   Raw opacity samples
            "orf",//ORF  DNG       r--   Olympus Digital Camera Raw Image File
            //OTB* OTB       rw-   On-the-air bitmap
            //OTF* TTF       r--   Open Type font (Freetype 2.3.5)
            //PAL* UYVY      rw-   16bit/pixel interleaved YUV
            //PALM* PALM      rw+   Palm pixmap
            //PAM* PNM       rw+   Common 2-dimensional bitmap format
            //PATTERN* PATTERN   r--   Predefined pattern
            //PBM* PNM       rw+   Portable bitmap format (black and white)
            "pcd",//PCD* PCD       rw-   Photo CD
            //PCDS* PCD       rw-   Photo CD
            "pcl",//PCL  PCL       rw-   Printer Control Language
            "pct",//PCT* PICT      rw-   Apple Macintosh QuickDraw/PICT
            "pcx",//PCX* PCX       rw-   ZSoft IBM PC Paintbrush
            "pdb",//PDB* PDB       rw+   Palm Database ImageViewer Format
            //PDF  PDF       rw+   Portable Document Format
            "pef",//PEF  DNG       r--   Pentax Electronic File
            "pfa",//PFA* TTF       r--   Postscript Type 1 font (ASCII) (Freetype 2.3.5)
            "pfb",//PFB* TTF       r--   Postscript Type 1 font (binary) (Freetype 2.3.5)
            //PFM* PFM       rw+   Portable float format
            //PGM* PNM       rw+   Portable graymap format (gray scale)
            //PGX* PGX       r--   JPEG-2000 VM Format
            //PICON* XPM       rw-   Personal Icon
            //PICT* PICT      rw-   Apple Macintosh QuickDraw/PICT
            //PIX* PIX       r--   Alias/Wavefront RLE image format
            //PJPEG* JPEG      rw-   Progessive Joint Photographic Experts Group JFIF
            //PLASMA* PLASMA    r--   Plasma fractal image
            "png",//PNG* PNG       rw-   Portable Network Graphics (libpng 1.2.15beta5)
            //PNG24* PNG       rw-   opaque 24-bit RGB (zlib 1.2.3.3)
            //PNG32* PNG       rw-   opaque or transparent 32-bit RGBA
            //PNG8* PNG       rw-   8-bit indexed with optional binary transparency
            //PNM* PNM       rw+   Portable anymap
            //PPM* PNM       rw+   Portable pixmap format (color)
            //PREVIEW* PREVIEW   -w-   Show a preview an image enhancement, effect, or f/x
            "ps",//PS  PS        rw+   PostScript
            //PS2* PS2       -w+   Level II PostScript
            //PS3* PS3       -w+   Level III PostScript
            "psd",//PSD* PSD       rw+   Adobe Photoshop bitmap
            //PTIF* TIFF      rw-   Pyramid encoded TIFF
            //PWP* PWP       r--   Seattle Film Works
            //R* RAW       rw+   Raw red samples
            //RAF  DNG       r--   Fuji CCD-RAW Graphic File
            //RAS* SUN       rw+   SUN Rasterfile
            //RGB* RGB       rw+   Raw red, green, and blue samples
            //RGBA* RGB       rw+   Raw red, green, blue, and alpha samples
            //RGBO* RGB       rw+   Raw red, green, blue, and opacity samples
            //RLA* RLA       r--   Alias/Wavefront image
            //RLE* RLE       r--   Utah Run length encoded image
            //SCR* SCR       r--   ZX-Spectrum SCREEN$
            //SCT* SCT       r--   Scitex HandShake
            //SFW* SFW       r--   Seattle Film Works
            //SGI* SGI       rw+   Irix RGB image
            //SHTML* HTML      -w-   Hypertext Markup Language and a client-side image map
            //SR2  DNG       r--   Sony Raw Format 2
            //SRF  DNG       r--   Sony Raw Format
            //STEGANO* STEGANO   r--   Steganographic image
            //SUN* SUN       rw+   SUN Rasterfile
            "svg",//SVG* SVG       rw+   Scalable Vector Graphics (RSVG 2.20.0)
            "svgz",//SVGZ* SVG       rw+   Compressed Scalable Vector Graphics (RSVG 2.20.0)
            "text",//TEXT* TXT       rw+   Text
            //TGA* TGA       rw+   Truevision Targa image
            //THUMBNAIL* THUMBNAIL -w+   EXIF Profile Thumbnail
            "tif",
            "tiff",//TIFF* TIFF      rw+   Tagged Image File Format (LIBTIFF, Version 3.8.2)
            //TIFF64* TIFF      ---   Tagged Image File Format (64-bit) (LIBTIFF, Version 3.8.2)
            //TILE* TILE      r--   Tile image with a texture
            //TIM* TIM       r--   PSX TIM
            //TTC* TTF       r--   TrueType font collection (Freetype 2.3.5)
            "ttf",//TTF* TTF       r--   TrueType font (Freetype 2.3.5)
            "txt",//TXT* TXT       rw+   Text
            //UIL* UIL       -w-   X-Motif UIL table
            //UYVY* UYVY      rw-   16bit/pixel interleaved YUV
            //VDA* TGA       rw+   Truevision Targa image
            //VICAR* VICAR     rw-   VICAR rasterfile format
            //VID* VID       rw+   Visual Image Directory
            //VIFF* VIFF      rw+   Khoros Visualization image
            //VST* TGA       rw+   Truevision Targa image
            //WBMP* WBMP      rw-   Wireless Bitmap (level 0) image
            "wmf",//WMF* WMF       ---   Windows Meta File
            //WMZ* WMZ       ---   Compressed Windows Meta File
            //WPG* WPG       r--   Word Perfect Graphics
            //X* X         rw+   X Image
            //X3F  DNG       r--   Sigma Camera RAW Picture File
            //XBM* XBM       rw-   X Windows system bitmap (black and white)
            //XC* XC        r--   Constant image uniform color
            //XCF* XCF       r--   GIMP image
            //XPM* XPM       rw-   X Windows system pixmap (color)
            //XV* VIFF      rw+   Khoros Visualization image
            //XWD* XWD       rw-   X Windows system window dump (color)
            //Y* RAW       rw+   Raw yellow samples
            //YCbCr* YCbCr     rw+   Raw Y, Cb, and Cr samples
            //YCbCrA* YCbCr     rw+   Raw Y, Cb, Cr, and opacity samples
            "yuv"//YUV* YUV       rw-   CCIR 601 4:1:1 or 4:2:2
    };

    public ImageConverter() {
        capableFileTypes = IMAGE_FILE;
    }

    public MediaInfo transcode(File input, File outputPath) {
        MediaInfo mediaInfo = new MediaInfo();
        // if input image is not JPEG, convert it to JPEG
        if (!FilenameUtils.isExtension(input.getName(), new String[]{"jpg", "jpeg"})) {
            String filename = "image.jpg";
            File output = new File(outputPath, filename);
            if (imageMagickConvert(input, output)) {
                Image image = getImageInfo(output);
                mediaInfo.setFilename(filename);
                mediaInfo.setImage(image);
            }
        }
        if (mediaInfo.getImage() == null) {
            Image image = getImageInfo(input);
            mediaInfo.setFilename(input.getName());
            mediaInfo.setImage(image);
        }
        String thumbnail = "thumbnail.gif";
        if (input.getName().contains(thumbnail))
            thumbnail = "thums.gif";
        File thumbnailFile = new File(outputPath, thumbnail);
        if (generateThumbnail(input, thumbnailFile)) {
            if (thumbnailFile.exists())
                mediaInfo.setThumbnail(thumbnail);
        }
        // if current image is bigger than small size, generate small image
        if ((mediaInfo.getImage().getWidth() > IMAGE_SMALL[0]) || (mediaInfo.getImage().getHeight() > IMAGE_SMALL[1])) {
            String filename = "image-s.jpg";
            imageMagickConvert(input, new File(outputPath, filename), IMAGE_SMALL[0], IMAGE_SMALL[1]);
        }
        // if current image is bigger than medium size, generate medium image
        if ((mediaInfo.getImage().getWidth() > IMAGE_MEDIUM) || (mediaInfo.getImage().getHeight() > IMAGE_MEDIUM)) {
            String filename = "image-m.jpg";
            imageMagickConvert(input, new File(outputPath, filename), IMAGE_MEDIUM, IMAGE_MEDIUM);
        }
        // if current image is bigger than large size, generate large image
        if ((mediaInfo.getImage().getWidth() > IMAGE_LARGE) || (mediaInfo.getImage().getHeight() > IMAGE_LARGE)) {
            String filename = "image-l.jpg";
            imageMagickConvert(input, new File(outputPath, filename), IMAGE_LARGE, IMAGE_LARGE);
        }
        // if current image is bigger than very large size, generate very large image
        if ((mediaInfo.getImage().getWidth() > IMAGE_VERY_LARGE) || (mediaInfo.getImage().getHeight() > IMAGE_VERY_LARGE)) {
            String filename = "image-v.jpg";
            imageMagickConvert(input, new File(outputPath, filename), IMAGE_VERY_LARGE, IMAGE_VERY_LARGE);
        }
        // if current image is bigger than extra large size, generate extra large image
        if ((mediaInfo.getImage().getWidth() > IMAGE_EXTRA_LARGE) || (mediaInfo.getImage().getHeight() > IMAGE_EXTRA_LARGE)) {
            String filename = "image-e.jpg";
            imageMagickConvert(input, new File(outputPath, filename), IMAGE_EXTRA_LARGE, IMAGE_EXTRA_LARGE);
        }
        return mediaInfo;
    }
}
