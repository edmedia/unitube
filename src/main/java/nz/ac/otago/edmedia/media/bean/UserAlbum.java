package nz.ac.otago.edmedia.media.bean;

import java.util.HashSet;
import java.util.Set;


/**
 * UserAlbum bean. This bean has the following fields:
 * <p/>
 * <ul>
 * <li><b>id </b>- ID</li>
 * <li><b>user </b>- User</li>
 * <li><b>album </b>- Album</li>
 * </ul>
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class UserAlbum {

    // --------------------------- VARIABLES START ----------------------------

    /**
     * ID
     */
    private Long id;

    /**
     * User
     */
    private User user;

    private Long userID;

    /**
     * Album
     */
    private Album album;

    private Long albumID;


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
     * Returns User.
     *
     * @return user
     */
    public User getUser() {
        return this.user;
    }

    public Long getUserID() {
        return this.userID;
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
     * Sets User.
     *
     * @param user User
     */
    public void setUser(User user) {
        this.user = user;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
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
     * @return a short name which should meaningfully identify this row to the user
     */
    public String getMeaningfulName() {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getUserName());
        sb.append("-");
        sb.append(album.getAlbumName());
        sb.append("");
        return sb.toString();
    }

    /**
     * @return the parent of this object
     */
    public User getParentClass() {
        return getUser();
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