package nz.ac.otago.edmedia.media.bean;

import java.util.Date;


/**
 * AccessRecord bean. This bean has the following fields:
 * <p/>
 * <ul>
 * <li><b>id </b>- ID</li>
 * <li><b>mediaID </b>- media ID</li>
 * <li><b>url </b>- url</li>
 * <li><b>action </b>- action(1: Upload, 2: Update, 3: View, 4: Delete)</li>
 * <li><b>filename </b>- filename</li>
 * <li><b>userID </b>- who</li>
 * <li><b>actionTime </b>- action time</li>
 * <li><b>ipAddress </b>- IP address</li>
 * <li><exb>mediaType </b>- Media Type(1: Other, 5: Image, 10: Audio, 20: Video)</li>
 * <li><b>fromExternal </b>- internal or external</li>
 * </ul>
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 24/02/12
 *         Time: 3:50 PM
 */
public class AccessRecord {

    // --------------------------- VARIABLES START ----------------------------

    /**
     * ID
     */
    private Long id;

    /**
     * media ID
     */
    private Long mediaID;

    /**
     * url
     */
    private String url;

    /**
     * action(1: Upload, 2: Update, 3: View, 4: Delete)
     */
    private int action;

    /**
     * filename
     */
    private String filename;

    /**
     * who
     */
    private Long userID;

    /**
     * action time
     */
    private Date actionTime;

    /**
     * IP address
     */
    private String ipAddress;

    /**
     * Media Type(1: Other, 5: Image, 10: Audio, 20: Video)
     */
    private int mediaType;

    /**
     * internal or external
     */
    private boolean fromExternal;


    // --------------------------- GET METHODS START --------------------------

    /**
     * Returns ID.
     *
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Returns media ID.
     *
     * @return mediaID
     */
    public Long getMediaID() {
        return this.mediaID;
    }

    /**
     * Returns url.
     *
     * @return url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Returns action(1: Upload, 2: Update, 3: View, 4: Delete).
     *
     * @return action
     */
    public int getAction() {
        return this.action;
    }

    /**
     * Returns filename.
     *
     * @return filename
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Returns who.
     *
     * @return userID
     */
    public Long getUserID() {
        return this.userID;
    }

    /**
     * Returns action time.
     *
     * @return actionTime
     */
    public Date getActionTime() {
        return this.actionTime;
    }

    /**
     * Returns IP address.
     *
     * @return ipAddress
     */
    public String getIpAddress() {
        return this.ipAddress;
    }

    /**
     * Returns Media Type(1: Other, 5: Image, 10: Audio, 20: Video).
     *
     * @return mediaType
     */
    public int getMediaType() {
        return this.mediaType;
    }

    /**
     * Returns internal or external.
     *
     * @return fromExternal
     */
    public boolean getFromExternal() {
        return this.fromExternal;
    }


    // --------------------------- SET METHODS START --------------------------

    /**
     * Sets ID.
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets media ID.
     *
     * @param mediaID media ID
     */
    public void setMediaID(Long mediaID) {
        this.mediaID = mediaID;
    }

    /**
     * Sets url.
     *
     * @param url url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets action.
     *
     * @param action action
     */
    public void setAction(int action) {
        this.action = action;
    }

    /**
     * Sets filename.
     *
     * @param filename filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Sets who.
     *
     * @param userID who
     */
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    /**
     * Sets action time.
     *
     * @param actionTime action time
     */
    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    /**
     * Sets IP address.
     *
     * @param ipAddress IP address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Sets Media Type(1: Other, 5: Image, 10: Audio, 20: Video).
     *
     * @param mediaType Media Type(1: Other, 5: Image, 10: Audio, 20: Video)
     */
    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * Sets internal or external.
     *
     * @param fromExternal internal or external
     */
    public void setFromExternal(boolean fromExternal) {
        this.fromExternal = fromExternal;
    }

    /**
     * Returns a string representation of the object.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("id=\"");
        sb.append(getId());
        sb.append("\" ");
        sb.append("mediaID=\"");
        sb.append(getMediaID());
        sb.append("\" ");
        sb.append("url=\"");
        sb.append(getUrl());
        sb.append("\" ");
        sb.append("action=\"");
        sb.append(getAction());
        sb.append("\" ");
        sb.append("filename=\"");
        sb.append(getFilename());
        sb.append("\" ");
        sb.append("userID=\"");
        sb.append(getUserID());
        sb.append("\" ");
        sb.append("actionTime=\"");
        sb.append(getActionTime());
        sb.append("\" ");
        sb.append("ipAddress=\"");
        sb.append(getIpAddress());
        sb.append("\" ");
        sb.append("mediaType=\"");
        sb.append(getMediaType());
        sb.append("\" ");
        sb.append("fromExternal=\"");
        sb.append(getFromExternal());
        sb.append("\" ");
        sb.append("]");
        return sb.toString();
    }

}