package nz.ac.otago.edmedia.media.bean;

import java.util.HashSet;
import java.util.Set;


/**
 * AccessRule bean. This bean has the following fields:
 * <p/>
 * <ul>
 * <li><b>id </b>- ID</li>
 * <li><b>media </b>- Media</li>
 * <li><b>user </b>- User</li>
 * <li><b>groupName </b>- Group Name</li>
 * <li><b>groupUsersLink </b>- Group Users Link</li>
 * <li><b>userInput </b>- User Input</li>
 * </ul>
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class AccessRule {

    // --------------------------- VARIABLES START ----------------------------

    /**
     * ID
     */
    private Long id;

    /**
     * Media
     */
    private Media media;

    private Long mediaID;

    /**
     * User
     */
    private User user;

    private Long userID;

    /**
     * Group Name
     */
    private String groupName;

    /**
     * Group Users Link
     */
    private String groupUsersLink;

    /**
     * User Input
     */
    private String userInput;


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
     * Returns Group Name.
     *
     * @return groupName
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * Returns Group Users Link.
     *
     * @return groupUsersLink
     */
    public String getGroupUsersLink() {
        return this.groupUsersLink;
    }

    /**
     * Returns User Input.
     *
     * @return userInput
     */
    public String getUserInput() {
        return this.userInput;
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
     * Sets Group Name.
     *
     * @param groupName Group Name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Sets Group Users Link.
     *
     * @param groupUsersLink Group Users Link
     */
    public void setGroupUsersLink(String groupUsersLink) {
        this.groupUsersLink = groupUsersLink;
    }

    /**
     * Sets User Input.
     *
     * @param userInput User Input
     */
    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }


    /**
     * @return a short name which should meaningfully identify this row to the user
     */
    public String getMeaningfulName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getMedia());
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
        sb.append("groupName=\"");
        sb.append(getGroupName());
        sb.append("\" ");
        sb.append("groupUsersLink=\"");
        sb.append(getGroupUsersLink());
        sb.append("\" ");
        sb.append("userInput=\"");
        sb.append(getUserInput());
        sb.append("\" ");
        sb.append("]");
        return sb.toString();
    }

}