package nz.ac.otago.edmedia.media.bean;

import java.util.HashSet;
import java.util.Set;


/**
 * AlbumMedia bean. This bean has the following fields:
 * <p/>
 * <ul>
 * <li><b>id </b>- ID</li>
 * <li><b>album </b>- Album</li>
 * <li><b>media </b>- Media</li>
 * <li><b>orderNumber </b>- order number</li>
 * </ul>
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class AlbumMedia {

    // --------------------------- VARIABLES START ----------------------------

    /**
     * ID
     */
    private Long id;

    /**
     * Album
     */
    private Album album;

    private Long albumID;

    /**
     * Media
     */
    private Media media;

    private Long mediaID;

    private int orderNumber;


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
     * Returns Album.
     *
     * @return album
     */
    public Album getAlbum() {
        return this.album;
    }

    public Long getAlbumID() {
        return this.albumID;
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

    public int getOrderNumber() {
        return this.orderNumber;
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
     * Sets Album.
     *
     * @param album Album
     */
    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setAlbumID(Long albumID) {
        this.albumID = albumID;
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

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return a short name which should meaningfully identify this row to the user
     */
    public String getMeaningfulName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getAlbum());
        return sb.toString();
    }

    /**
     * @return the parent of this object
     */
    public Album getParentClass() {
        return getAlbum();
    }

    /**
     * Returns a string representation of the object.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("id=\"");
        sb.append(getId());
        sb.append("\" ");
        sb.append("]");
        return sb.toString();
    }

}