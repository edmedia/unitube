package nz.ac.otago.edmedia.media.bean;

/**
 * Media Information.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 21/01/2008
 *         Time: 15:03:43
 */
public class MediaInfo {

    private String duration;

    private int bitrate;

    /**
     * the filename of converted file
     */
    private String filename;

    /**
     * the filename of thumnail
     */
    private String thumbnail;

    private int width;

    private int height;

    private Video video;

    private Audio audio;

    private Flash flash;

    private Image image;

    private Images images;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
        if (video != null) {
            this.width = video.getWidth();
            this.height = video.getHeight();
        }
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Flash getFlash() {
        return flash;
    }

    public void setFlash(Flash flash) {
        this.flash = flash;
        if (flash != null) {
            this.width = flash.getWidth();
            this.height = flash.getHeight();
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        if (image != null) {
            this.width = image.getWidth();
            this.height = image.getHeight();
        }
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
        if ((images != null) && (images.getImage() != null)) {
            this.width = images.getImage().getWidth();
            this.height = images.getImage().getHeight();
        }
    }

    public MediaInfo() {

    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("duration:");
        sb.append(getDuration());
        sb.append(" bitrate:");
        sb.append(getBitrate());
        sb.append(" width:");
        sb.append(getWidth());
        sb.append(" height:");
        sb.append(getHeight());
        if (audio != null) {
            sb.append(" Audio:");
            sb.append(getAudio());
        }
        if (video != null) {
            sb.append(" Video:");
            sb.append(getVideo());
        }
        sb.append("]");
        return sb.toString();
    }

}
