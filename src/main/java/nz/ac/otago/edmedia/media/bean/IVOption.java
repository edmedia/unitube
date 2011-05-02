package nz.ac.otago.edmedia.media.bean;

import java.util.HashSet;
import java.util.Set;


/**
 * IVOption bean. This bean has the following fields:
 * <p/>
 * <ul>
 * <li><b>id </b>- ID</li>
 * <li><b>actualWidth </b>- Actual Width</li>
 * <li><b>acturalWidthUnit </b>- Actual Width Unit</li>
 * <li><b>minZoom </b>- Minimum Zoom</li>
 * <li><b>maxZoom </b>- Maximum Zoom</li>
 * <li><b>displayMeasureTool </b>- Display Measurement tool and grid?</li>
 * <li><b>otherCanAnnotate </b>- Other can annotate this image?</li>
 * <li><b>whichImageForIV </b>- Which Image for ImageViewer to use?</li>
 * <li><b>media </b>- Media</li>
 * </ul>
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class IVOption {

    // --------------------------- VARIABLES START ----------------------------

    /**
     * ID
     */
    private Long id;

    /**
     * Actual Width
     */
    private float actualWidth;

    /**
     * Actual Width Unit
     */
    private String acturalWidthUnit;

    /**
     * Minimum Zoom
     */
    private int minZoom;

    /**
     * Maximum Zoom
     */
    private int maxZoom;

    /**
     * Display Measurement tool and grid?
     */
    private boolean displayMeasureTool;

    /**
     * Other can annotate this image?
     */
    private boolean otherCanAnnotate;

    /**
     * Which Image for ImageViewer to use?
     */
    private String whichImageForIV;

    /**
     * Media
     */
    private Media media;

    private Long mediaID;


    // --------------------------- RELATIONSHIP MANAGEMENT --------------------

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
     * Returns Actual Width.
     *
     * @return actualWidth
     */
    public float getActualWidth() {
        return this.actualWidth;
    }

    /**
     * Returns Actual Width Unit.
     *
     * @return acturalWidthUnit
     */
    public String getActuralWidthUnit() {
        return this.acturalWidthUnit;
    }

    /**
     * Returns Minimum Zoom.
     *
     * @return minZoom
     */
    public int getMinZoom() {
        return this.minZoom;
    }

    /**
     * Returns Maximum Zoom.
     *
     * @return maxZoom
     */
    public int getMaxZoom() {
        return this.maxZoom;
    }

    /**
     * Returns Display Measurement tool and grid?.
     *
     * @return displayMeasureTool
     */
    public boolean getDisplayMeasureTool() {
        return this.displayMeasureTool;
    }

    /**
     * Returns Other can annotate this image?.
     *
     * @return otherCanAnnotate
     */
    public boolean getOtherCanAnnotate() {
        return this.otherCanAnnotate;
    }

    /**
     * Returns Which Image for ImageViewer to use?.
     *
     * @return whichImageForIV
     */
    public String getWhichImageForIV() {
        return this.whichImageForIV;
    }

    /**
     * Returns Media.
     *
     * @return media
     */
    public Media getMedia() {
        return this.media;
    }

    public Long getMediaID() {
        return this.mediaID;
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
     * Sets Actual Width.
     *
     * @param actualWidth Actual Width
     */
    public void setActualWidth(float actualWidth) {
        this.actualWidth = actualWidth;
    }

    /**
     * Sets Actual Width Unit.
     *
     * @param acturalWidthUnit Actual Width Unit
     */
    public void setActuralWidthUnit(String acturalWidthUnit) {
        this.acturalWidthUnit = acturalWidthUnit;
    }

    /**
     * Sets Minimum Zoom.
     *
     * @param minZoom Minimum Zoom
     */
    public void setMinZoom(int minZoom) {
        this.minZoom = minZoom;
    }

    /**
     * Sets Maximum Zoom.
     *
     * @param maxZoom Maximum Zoom
     */
    public void setMaxZoom(int maxZoom) {
        this.maxZoom = maxZoom;
    }

    /**
     * Sets Display Measurement tool and grid?.
     *
     * @param displayMeasureTool Display Measurement tool and grid?
     */
    public void setDisplayMeasureTool(boolean displayMeasureTool) {
        this.displayMeasureTool = displayMeasureTool;
    }

    /**
     * Sets Other can annotate this image?.
     *
     * @param otherCanAnnotate Other can annotate this image?
     */
    public void setOtherCanAnnotate(boolean otherCanAnnotate) {
        this.otherCanAnnotate = otherCanAnnotate;
    }

    /**
     * Sets Which Image for ImageViewer to use?.
     *
     * @param whichImageForIV Which Image for ImageViewer to use?
     */
    public void setWhichImageForIV(String whichImageForIV) {
        this.whichImageForIV = whichImageForIV;
    }

    /**
     * Sets Media.
     *
     * @param media Media
     */
    public void setMedia(Media media) {
        this.media = media;
    }

    public void setMediaID(Long mediaID) {
        this.mediaID = mediaID;
    }


    /**
     * @return a short name which should meaningfully identify this row to the user
     */
    public String getMeaningfulName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getActualWidth());
        return sb.toString();
    }

    /**
     * Returns a string representation of the object.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("id=\"");
        sb.append(getId());
        sb.append("\" ");
        sb.append("actualWidth=\"");
        sb.append(getActualWidth());
        sb.append("\" ");
        sb.append("acturalWidthUnit=\"");
        sb.append(getActuralWidthUnit());
        sb.append("\" ");
        sb.append("minZoom=\"");
        sb.append(getMinZoom());
        sb.append("\" ");
        sb.append("maxZoom=\"");
        sb.append(getMaxZoom());
        sb.append("\" ");
        sb.append("displayMeasureTool=\"");
        sb.append(getDisplayMeasureTool());
        sb.append("\" ");
        sb.append("otherCanAnnotate=\"");
        sb.append(getOtherCanAnnotate());
        sb.append("\" ");
        sb.append("whichImageForIV=\"");
        sb.append(getWhichImageForIV());
        sb.append("\" ");
        sb.append("]");
        return sb.toString();
    }

}