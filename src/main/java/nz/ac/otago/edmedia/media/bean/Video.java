package nz.ac.otago.edmedia.media.bean;

/**
 * Video Information.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 21/01/2008
 *         Time: 15:04:43
 */
public class Video {

    String format;

    int width;

    int height;

    int rate;

    float freq;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public float getFreq() {
        return freq;
    }

    public void setFreq(float freq) {
        this.freq = freq;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(format);
        sb.append(" ");
        sb.append(width);
        sb.append("x");
        sb.append(height);
        sb.append(" ");
        sb.append(rate);
        sb.append("Kb/s ");
        sb.append(freq);
        sb.append("fps ");
        sb.append("]");
        return sb.toString();
    }

}
