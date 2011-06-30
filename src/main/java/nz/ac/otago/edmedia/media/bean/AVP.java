package nz.ac.otago.edmedia.media.bean;

import nz.ac.otago.edmedia.spring.bean.WebID;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;


/**
 * Album bean. This bean has the following fields:
 * <p/>
 * <ul>
 * <li><b>id </b>- ID</li>
 * <li><b>title </b>- AVP title</li>
 * <li><b>description </b>- AVP Description</li>
 * <li><b>randomCode </b>- Random Code</li>
 * <li><b>owner </b>- Owner of this AVP</li>
 * <li><b>av1 </b>- Audio/Video one</li>
 * <li><b>av2 </b>- Audio/Video two</li>
 * <li><b>presentation </b>- Presentation</li>
 * </ul>
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class AVP extends WebID {

    // --------------------------- VARIABLES START ----------------------------

    /**
     * ID
     */
    private Long id;

    /**
     * AVP title
     */
    private String title;

    /**
     * AVP Description
     */
    private String description;

    /**
     * Random Code
     */
    private String randomCode;

    /**
     * Owner of this album
     */
    private User owner;

    private Long ownerID;

    /**
     * Audio/Video one
     */
    private Media av1;

    private Long av1ID;

    /**
     * Audio/Video two
     */
    private Media av2;

    private Long av2ID;

    /**
     * Presentation
     */
    private Media presentation;

    private Long presentationID;

    /**
     * All SlideInfos
     */
    private Set<SlideInfo> slideInfos = new HashSet<SlideInfo>();
    private Long[] slideInfosID;


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
     * Returns AVP title.
     *
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns AVP Description.
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns Random Code.
     *
     * @return randomCode
     */
    public String getRandomCode() {
        return this.randomCode;
    }

    /**
     * Returns Owner of this album.
     *
     * @return owner
     */
    public User getOwner() {
        return this.owner;
    }

    public Long getOwnerID() {
        return this.ownerID;
    }

    /**
     * Returns Audio/Video one.
     *
     * @return av1
     */
    public Media getAv1() {
        return this.av1;
    }

    public Long getAv1ID() {
        return this.av1ID;
    }


    /**
     * Returns Audio/Video two.
     *
     * @return av2
     */
    public Media getAv2() {
        return this.av2;
    }

    public Long getAv2ID() {
        return this.av2ID;
    }


    /**
     * Returns Presentation.
     *
     * @return presentation
     */
    public Media getPresentation() {
        return this.presentation;
    }

    public Long getPresentationID() {
        return this.presentationID;
    }


    /**
     * Returns All SlideInfos.
     *
     * @return slideInfos
     */
    public Set<SlideInfo> getSlideInfos() {
        return this.slideInfos;
    }

    public Long[] getSlideInfosID() {
        return this.slideInfosID;
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
     * Sets AVP title.
     *
     * @param title AVP title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets AVP Description.
     *
     * @param description AVP Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets Random Code.
     *
     * @param randomCode Random Code
     */
    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    /**
     * Sets Owner of this album.
     *
     * @param owner Owner of this album
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setOwnerID(Long ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * Sets Audio/Video one.
     *
     * @param av1 Audio/Video one
     */
    public void setAv1(Media av1) {
        this.av1 = av1;
    }

    public void setAv1ID(Long av1ID) {
        this.av1ID = av1ID;
    }

    /**
     * Sets Audio/Video two.
     *
     * @param av2 Audio/Video two
     */
    public void setAv2(Media av2) {
        this.av2 = av2;
    }

    public void setAv2ID(Long av2ID) {
        this.av2ID = av2ID;
    }


    /**
     * Sets Presentation.
     *
     * @param presentation Presentation
     */
    public void setPresentation(Media presentation) {
        this.presentation = presentation;
    }

    public void setPresentationID(Long presentationID) {
        this.presentationID = presentationID;
    }


    /**
     * Sets All SlideInfos.
     *
     * @param slideInfos All SlideInfos
     */
    public void setSlideInfos(Set<SlideInfo> slideInfos) {
        this.slideInfos = slideInfos;
    }

    public void setSlideInfosID(Long[] slideInfosID) {
        this.slideInfosID = slideInfosID;
    }

    /**
     * @return a short name which should meaningfully identify this row to the user
     */
    public String getMeaningfulName() {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(getTitle()))
            sb.append(getTitle());
        else
            sb.append(presentation.getTitle());
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
        sb.append("title=\"");
        sb.append(getTitle());
        sb.append("\" ");
        sb.append("description=\"");
        sb.append(getDescription());
        sb.append("\" ");
        sb.append("randomCode=\"");
        sb.append(getRandomCode());
        sb.append("\" ");
        sb.append("]");
        return sb.toString();
    }

}