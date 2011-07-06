package nz.ac.otago.edmedia.media.bean;

/**
 * Slide info
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: June 27, 2011
 *         Time: 10:01 AM
 */
public class SlideInfo {

    /**
     * ID
     */
    private Long id;

    // start time
    private float sTime;

    // end time
    private float eTime;

    // which slide
    private int num;

    // title
    private String title;

    private AVP avp;

    private Long avpID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getsTime() {
        return sTime;
    }

    public void setsTime(float sTime) {
        this.sTime = sTime;
    }

    public float geteTime() {
        return eTime;
    }

    public void seteTime(float eTime) {
        this.eTime = eTime;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns AVP.
     *
     * @return avp
     */
    public AVP getAvp() {
        return this.avp;
    }

    public Long getAvpID() {
        return this.avpID;
    }


    /**
     * Sets AVP.
     *
     * @param avp avp object
     */
    public void setAvp(AVP avp) {
        this.avp = avp;
    }

    public void setAvpID(Long avpID) {
        this.avpID = avpID;
    }


}



