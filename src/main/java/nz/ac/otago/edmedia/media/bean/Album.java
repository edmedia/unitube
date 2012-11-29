package nz.ac.otago.edmedia.media.bean;

import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.bean.WebID;
import org.hibernate.search.annotations.*;

import java.util.HashSet;
import java.util.Set;


/**
 * Album bean. This bean has the following fields:
 * <p/>
 * <ul>
 * <li><b>id </b>- ID</li>
 * <li><b>albumName </b>- Album Name</li>
 * <li><b>description </b>- Album Description</li>
 * <li><b>randomCode </b>- Random Code</li>
 * <li><b>accessType </b>- access type(0: public, 10: hidden, 20: private)</li>
 * <li><b>owner </b>- Owner of this album</li>
 * <li><b>albumMedias </b>- All AlbumMedias</li>
 * <li><b>userAlbums </b>- All UserAlbums</li>
 * </ul>
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
@Indexed
public class Album extends WebID {

    // --------------------------- VARIABLES START ----------------------------

    /**
     * ID
     */
    @DocumentId
    private Long id;

    /**
     * Album Name
     */
    @Field(index = Index.TOKENIZED, store = Store.NO)
    private String albumName;

    /**
     * Album Description
     */
    @Field(index = Index.TOKENIZED, store = Store.NO)
    private String description;

    /**
     * Random Code
     */
    private String randomCode;

    /**
     * access type(0: public, 10: hidden, 20: private)
     */
    private int accessType;

    /**
     * Owner of this album
     */
    private User owner;

    private Long ownerID;

    /**
     * All AlbumMedias
     */
    private Set<AlbumMedia> albumMedias = new HashSet<AlbumMedia>();
    private Long[] albumMediasID;

    /**
     * All UserAlbums
     */
    private Set<UserAlbum> userAlbums = new HashSet<UserAlbum>();
    private Long[] userAlbumsID;


    // --------------------------- RELATIONSHIP MANAGEMENT --------------------

    /**
     * @param albumMedia to add
     */
    public void addAlbumMedias(AlbumMedia albumMedia) {
        albumMedia.setAlbum(this);
        albumMedias.add(albumMedia);
    }

    /**
     * @param albumMedia to remove
     */
    public void removeAlbumMedias(AlbumMedia albumMedia) {
        albumMedia.setAlbum(null);
        albumMedias.remove(albumMedia);
    }

    /**
     * @return a new AlbumMedia
     */
    public AlbumMedia createAlbumMedias() {
        AlbumMedia newAlbumMedia = new AlbumMedia();
        addAlbumMedias(newAlbumMedia);
        return newAlbumMedia;
    }

    /**
     * @param userAlbum to add
     */
    public void addUserAlbums(UserAlbum userAlbum) {
        userAlbum.setAlbum(this);
        userAlbums.add(userAlbum);
    }

    /**
     * @param userAlbum to remove
     */
    public void removeUserAlbums(UserAlbum userAlbum) {
        userAlbum.setAlbum(null);
        userAlbums.remove(userAlbum);
    }

    /**
     * @return a new UserAlbum
     */
    public UserAlbum createUserAlbums() {
        UserAlbum newUserAlbum = new UserAlbum();
        addUserAlbums(newUserAlbum);
        return newUserAlbum;
    }

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
     * Returns Album Name.
     *
     * @return albumName
     */
    public String getAlbumName() {
        return this.albumName;
    }

    /**
     * Returns Album Description.
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
     * Returns access type(0: public, 10: hidden, 20: private).
     *
     * @return accessType
     */
    public int getAccessType() {
        return this.accessType;
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
     * Returns how many public finished media files this album has
     *
     * @return how many public finished media files this album has
     */
    public int getMediaNum() {
        int mediaNum = 0;
        for (AlbumMedia am : albumMedias) {
            if (MediaUtil.isVisible(am.getAlbum(), am.getMedia()))
                mediaNum++;
        }
        return mediaNum;
    }

    /**
     * Returns All AlbumMedias.
     *
     * @return albumMedias
     */
    public Set<AlbumMedia> getAlbumMedias() {
        return this.albumMedias;
    }

    public Long[] getAlbumMediasID() {
        return this.albumMediasID;
    }

    /**
     * Returns All UserAlbums.
     *
     * @return userAlbums
     */
    public Set<UserAlbum> getUserAlbums() {
        return this.userAlbums;
    }

    public Long[] getUserAlbumsID() {
        return this.userAlbumsID;
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
     * Sets Album Name.
     *
     * @param albumName Album Name
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * Sets Album Description.
     *
     * @param description Album Description
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
     * Sets access type(0: public, 10: hidden, 20: private).
     *
     * @param accessType access type(0: public, 10: hidden, 20: private)
     */
    public void setAccessType(int accessType) {
        this.accessType = accessType;
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
     * Sets All AlbumMedias.
     *
     * @param albumMedias All AlbumMedias
     */
    public void setAlbumMedias(Set<AlbumMedia> albumMedias) {
        this.albumMedias = albumMedias;
    }

    public void setAlbumMediasID(Long[] albumMediasID) {
        this.albumMediasID = albumMediasID;
    }

    /**
     * Sets All UserAlbums.
     *
     * @param userAlbums All UserAlbums
     */
    public void setUserAlbums(Set<UserAlbum> userAlbums) {
        this.userAlbums = userAlbums;
    }

    public void setUserAlbumsID(Long[] userAlbumsID) {
        this.userAlbumsID = userAlbumsID;
    }


    /**
     * @return a short name which should meaningfully identify this row to the user
     */
    public String getMeaningfulName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getAlbumName());
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
        sb.append("albumName=\"");
        sb.append(getAlbumName());
        sb.append("\" ");
        sb.append("description=\"");
        sb.append(getDescription());
        sb.append("\" ");
        sb.append("randomCode=\"");
        sb.append(getRandomCode());
        sb.append("\" ");
        sb.append("accessType=\"");
        sb.append(getAccessType());
        sb.append("\" ");
        sb.append("]");
        return sb.toString();
    }

}