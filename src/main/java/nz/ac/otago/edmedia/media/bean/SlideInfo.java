package nz.ac.otago.edmedia.media.bean;

import java.rmi.server.ObjID;

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
    private double sTime;

    // end time
    private double eTime;

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

    public double getsTime() {
        return sTime;
    }

    public void setsTime(double sTime) {
        this.sTime = sTime;
    }

    public double geteTime() {
        return eTime;
    }

    public void seteTime(double eTime) {
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



