package nz.ac.otago.edmedia.media.bean;

import nz.ac.otago.edmedia.spring.bean.WebID;
import java.util.HashSet;
import java.util.Set;


/**
 * Annotation bean. This bean has the following fields:
 * <p/>
 * <ul>
 * <li><b>id </b>- ID</li>
 * <li><b>annotName </b>- Annotation Name</li>
 * <li><b>description </b>- Annotation Description</li>
 * <li><b>annotFile </b>- Annotation File</li>
 * <li><b>annotTime </b>- Annotation Time</li>
 * <li><b>randomCode </b>- Random Code</li>
 * <li><b>media </b>- Media</li>
 * <li><b>author </b>- Author</li>
 * </ul>
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class Annotation extends WebID {

    // --------------------------- VARIABLES START ----------------------------

    /**
     * ID
     */
    private Long id;

    /**
     * Annotation Name
     */
    private String annotName;

    /**
     * Annotation Description
     */
    private String description;

    /**
     * Annotation File
     */
    private org.springframework.web.multipart.MultipartFile annotFile;

    private String annotFileUserName;

    /**
     * Annotation Time
     */
    private java.util.Date annotTime;

    /**
     * Random Code
     */
    private String randomCode;

    /**
     * Media
     */
    private Media media;

    private Long mediaID;

    /**
     * Author
     */
    private User author;

    private Long authorID;


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
     * Returns Annotation Name.
     *
     * @return annotName
     */
    public String getAnnotName() {
        return this.annotName;
    }

    /**
     * Returns Annotation Description.
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns Annotation File.
     *
     * @return annotFile
     */
    public org.springframework.web.multipart.MultipartFile getAnnotFile() {
        return this.annotFile;
    }

    public String getAnnotFileUserName() {
        return this.annotFileUserName;
    }

    /**
     * Returns Annotation Time.
     *
     * @return annotTime
     */
    public java.util.Date getAnnotTime() {
        return this.annotTime;
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

    /**
     * Returns Author.
     *
     * @return author
     */
    public User getAuthor() {
        return this.author;
    }

    public Long getAuthorID() {
        return this.authorID;
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
     * Sets Annotation Name.
     *
     * @param annotName Annotation Name
     */
    public void setAnnotName(String annotName) {
        this.annotName = annotName;
    }

    /**
     * Sets Annotation Description.
     *
     * @param description Annotation Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets Annotation File.
     *
     * @param annotFile Annotation File
     */
    public void setAnnotFile(org.springframework.web.multipart.MultipartFile annotFile) {
        this.annotFile = annotFile;
    }

    public void setAnnotFileUserName(String annotFileUserName) {
        this.annotFileUserName = annotFileUserName;
    }

    /**
     * Sets Annotation Time.
     *
     * @param annotTime Annotation Time
     */
    public void setAnnotTime(java.util.Date annotTime) {
        this.annotTime = annotTime;
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
     * Sets Author.
     *
     * @param author Author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    public void setAuthorID(Long authorID) {
        this.authorID = authorID;
    }


    /**
     * @return a short name which should meaningfully identify this row to the user
     */
    public String getMeaningfulName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getAnnotName());
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
        sb.append("annotName=\"");
        sb.append(getAnnotName());
        sb.append("\" ");
        sb.append("description=\"");
        sb.append(getDescription());
        sb.append("\" ");
        sb.append("annotFile=\"");
        sb.append(getAnnotFile());
        sb.append("\" ");
        sb.append("annotTime=\"");
        sb.append(getAnnotTime());
        sb.append("\" ");
        sb.append("randomCode=\"");
        sb.append(getRandomCode());
        sb.append("\" ");
        sb.append("]");
        return sb.toString();
    }

}