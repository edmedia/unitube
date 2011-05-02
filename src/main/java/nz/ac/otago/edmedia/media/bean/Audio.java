package nz.ac.otago.edmedia.media.bean;

/**
 * Audio Information.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 21/01/2008
 *         Time: 15:04:28
 */
public class Audio {

    String format;

    int freq;

    boolean stereo;

    int rate;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public boolean isStereo() {
        return stereo;
    }

    public void setStereo(boolean stereo) {
        this.stereo = stereo;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(format);
        sb.append(" ");
        sb.append(freq);
        sb.append("Hz ");
        if (stereo)
            sb.append("stereo ");
        else
            sb.append("mono ");
        sb.append(rate);
        sb.append("Kb/s");
        sb.append("]");
        return sb.toString();
    }

}
