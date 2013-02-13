package nz.ac.otago.edmedia.media.bean;

import nz.ac.otago.edmedia.auth.bean.BaseUser;
import nz.ac.otago.edmedia.media.util.MediaUtil;
import nz.ac.otago.edmedia.spring.bean.WebID;
import org.hibernate.search.annotations.*;

import java.util.HashSet;
import java.util.Set;


/**
 * User bean. This bean has the following fields:
 * <p/>
 * <ul>
 * <li><b>id </b>- ID</li>
 * <li><b>userName </b>- User Name</li>
 * <li><b>passWord </b>- Pass Word</li>
 * <li><b>wayf </b>- Where Are You From</li>
 * <li><b>firstName </b>- First Name</li>
 * <li><b>lastName </b>- Last Name</li>
 * <li><b>email </b>- Email Address</li>
 * <li><b>mobile </b>- Mobile Number</li>
 * <li><b>uploadAccessType </b>- Default Upload Access Type</li>
 * <li><b>emailUploadAccessType </b>- Default Email Upload Access Type</li>
 * <li><b>loginTimes </b>- Login Times</li>
 * <li><b>firstLoginTime </b>- First Login Time</li>
 * <li><b>lastLoginTime </b>- Last Login Time</li>
 * <li><b>lastLoginIP </b>- Last Login IP</li>
 * <li><b>onlineTime </b>- Online Time(ms)</li>
 * <li><b>isGuest </b>- Is this user a guest?</li>
 * <li><b>disabled </b>- Disabled access?</li>
 * <li><b>randomCode </b>- Random Code</li>
 * <li><b>medias </b>- All Medias</li>
 * <li><b>userAlbums </b>- All UserAlbums</li>
 * </ul>
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
@Indexed
public class User extends WebID implements BaseUser {

    // --------------------------- VARIABLES START ----------------------------

    /**
     * ID
     */
    @DocumentId
    private Long id;

    /**
     * User Name
     */
    @Field(index = Index.TOKENIZED, store = Store.NO)
    private String userName;

    /**
     * Pass Word
     */
    private String passWord;

    /**
     * Where Are You From
     */
    private String wayf;

    /**
     * First Name
     */
    @Field(index = Index.TOKENIZED, store = Store.NO)
    private String firstName;

    /**
     * Last Name
     */
    @Field(index = Index.TOKENIZED, store = Store.NO)
    private String lastName;

    /**
     * Email Address
     */
    private String email;

    /**
     * Mobile Number
     */
    private String mobile;

    /**
     * Default Upload Access Type
     */
    private int uploadAccessType;

    /**
     * Default Email Upload Access Type
     */
    private int emailUploadAccessType;

    /**
     * Login Times
     */
    private int loginTimes;

    /**
     * First Login Time
     */
    private java.util.Date firstLoginTime;

    /**
     * Last Login Time
     */
    private java.util.Date lastLoginTime;

    /**
     * Last Login IP
     */
    private String lastLoginIP;

    /**
     * Online Time(ms)
     */
    private long onlineTime;

    /**
     * Is this user a guest?
     */
    private boolean isGuest;

    /**
     * Disabled access?
     */
    private boolean disabled;

    /**
     * Random Code
     */
    private String randomCode;

    /**
     * All Medias
     */
    private Set<Media> medias = new HashSet<Media>();
    private Long[] mediasID;

    /**
     * All UserAlbums
     */
    private Set<UserAlbum> userAlbums = new HashSet<UserAlbum>();
    private Long[] userAlbumsID;


    // --------------------------- RELATIONSHIP MANAGEMENT --------------------

    /**
     * @param media to add
     */
    public void addMedias(Media media) {
        media.setUser(this);
        medias.add(media);
    }

    /**
     * @param media to remove
     */
    public void removeMedias(Media media) {
        media.setUser(null);
        medias.remove(media);
    }

    /**
     * @return a new Media
     */
    public Media createMedias() {
        Media newMedia = new Media();
        addMedias(newMedia);
        return newMedia;
    }

    /**
     * @param userAlbum to add
     */
    public void addUserAlbums(UserAlbum userAlbum) {
        userAlbum.setUser(this);
        userAlbums.add(userAlbum);
    }

    /**
     * @param userAlbum to remove
     */
    public void removeUserAlbums(UserAlbum userAlbum) {
        userAlbum.setUser(null);
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
     * Returns User Name.
     *
     * @return userName
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Returns Pass Word.
     *
     * @return passWord
     */
    public String getPassWord() {
        return this.passWord;
    }

    /**
     * Returns Where Are You From.
     *
     * @return wayf
     */
    public String getWayf() {
        return this.wayf;
    }

    /**
     * Returns First Name.
     *
     * @return firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns Last Name.
     *
     * @return lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Returns Email Address.
     *
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Returns Mobile Number.
     *
     * @return mobile
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * Returns Default Upload Access Type.
     *
     * @return uploadAccessType
     */
    public int getUploadAccessType() {
        return this.uploadAccessType;
    }

    /**
     * Returns Default Email Upload Access Type.
     *
     * @return emailUploadAccessType
     */
    public int getEmailUploadAccessType() {
        return this.emailUploadAccessType;
    }

    /**
     * Returns Login Times.
     *
     * @return loginTimes
     */
    public int getLoginTimes() {
        return this.loginTimes;
    }

    /**
     * Returns First Login Time.
     *
     * @return firstLoginTime
     */
    public java.util.Date getFirstLoginTime() {
        return this.firstLoginTime;
    }

    /**
     * Returns Last Login Time.
     *
     * @return lastLoginTime
     */
    public java.util.Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    /**
     * Returns Last Login IP.
     *
     * @return lastLoginIP
     */
    public String getLastLoginIP() {
        return this.lastLoginIP;
    }

    /**
     * Returns Online Time(ms).
     *
     * @return onlineTime
     */
    public long getOnlineTime() {
        return this.onlineTime;
    }

    /**
     * Returns Is this user a guest?.
     *
     * @return isGuest
     */
    public boolean getIsGuest() {
        return this.isGuest;
    }

    /**
     * Returns Disabled access?.
     *
     * @return disabled
     */
    public boolean getDisabled() {
        return this.disabled;
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
     * Returns how many public finished media files this user has
     *
     * @return how many public finished media files this user has
     */
    public int getMediaNum() {
        return MediaUtil.getMediaNum(this);
    }

    /**
     * Returns how many public non-empty albums this user has
     *
     * @return how many public non-empty albums this user has
     */
    public int getAlbumNum() {
        return MediaUtil.getAlbumNum(this);
    }

    /**
     * Returns All Medias.
     *
     * @return medias
     */
    public Set<Media> getMedias() {
        return this.medias;
    }

    public Long[] getMediasID() {
        return this.mediasID;
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
     * Sets User Name.
     *
     * @param userName User Name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets Pass Word.
     *
     * @param passWord Pass Word
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * Sets Where Are You From.
     *
     * @param wayf Where Are You From
     */
    public void setWayf(String wayf) {
        this.wayf = wayf;
    }

    /**
     * Sets First Name.
     *
     * @param firstName First Name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets Last Name.
     *
     * @param lastName Last Name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets Email Address.
     *
     * @param email Email Address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets Mobile Number.
     *
     * @param mobile Mobile Number
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Sets Default Upload Access Type.
     *
     * @param uploadAccessType Default Upload Access Type
     */
    public void setUploadAccessType(int uploadAccessType) {
        this.uploadAccessType = uploadAccessType;
    }

    /**
     * Sets Default Email Upload Access Type.
     *
     * @param emailUploadAccessType Default Email Upload Access Type
     */
    public void setEmailUploadAccessType(int emailUploadAccessType) {
        this.emailUploadAccessType = emailUploadAccessType;
    }

    /**
     * Sets Login Times.
     *
     * @param loginTimes Login Times
     */
    public void setLoginTimes(int loginTimes) {
        this.loginTimes = loginTimes;
    }

    /**
     * Sets First Login Time.
     *
     * @param firstLoginTime First Login Time
     */
    public void setFirstLoginTime(java.util.Date firstLoginTime) {
        this.firstLoginTime = firstLoginTime;
    }

    /**
     * Sets Last Login Time.
     *
     * @param lastLoginTime Last Login Time
     */
    public void setLastLoginTime(java.util.Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * Sets Last Login IP.
     *
     * @param lastLoginIP Last Login IP
     */
    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    /**
     * Sets Online Time(ms).
     *
     * @param onlineTime Online Time(ms)
     */
    public void setOnlineTime(long onlineTime) {
        this.onlineTime = onlineTime;
    }

    /**
     * Sets Is this user a guest?.
     *
     * @param isGuest Is this user a guest?
     */
    public void setIsGuest(boolean isGuest) {
        this.isGuest = isGuest;
    }

    /**
     * Sets Disabled access?.
     *
     * @param disabled Disabled access?
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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
     * Sets All Medias.
     *
     * @param medias All Medias
     */
    public void setMedias(Set<Media> medias) {
        this.medias = medias;
    }

    public void setMediasID(Long[] mediasID) {
        this.mediasID = mediasID;
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
        sb.append(getUserName());
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
        sb.append("userName=\"");
        sb.append(getUserName());
        sb.append("\" ");
        sb.append("passWord=\"");
        sb.append(getPassWord());
        sb.append("\" ");
        sb.append("wayf=\"");
        sb.append(getWayf());
        sb.append("\" ");
        sb.append("firstName=\"");
        sb.append(getFirstName());
        sb.append("\" ");
        sb.append("lastName=\"");
        sb.append(getLastName());
        sb.append("\" ");
        sb.append("email=\"");
        sb.append(getEmail());
        sb.append("\" ");
        sb.append("mobile=\"");
        sb.append(getMobile());
        sb.append("\" ");
        sb.append("uploadAccessType=\"");
        sb.append(getUploadAccessType());
        sb.append("\" ");
        sb.append("emailUploadAccessType=\"");
        sb.append(getEmailUploadAccessType());
        sb.append("\" ");
        sb.append("loginTimes=\"");
        sb.append(getLoginTimes());
        sb.append("\" ");
        sb.append("firstLoginTime=\"");
        sb.append(getFirstLoginTime());
        sb.append("\" ");
        sb.append("lastLoginTime=\"");
        sb.append(getLastLoginTime());
        sb.append("\" ");
        sb.append("lastLoginIP=\"");
        sb.append(getLastLoginIP());
        sb.append("\" ");
        sb.append("onlineTime=\"");
        sb.append(getOnlineTime());
        sb.append("\" ");
        sb.append("isGuest=\"");
        sb.append(getIsGuest());
        sb.append("\" ");
        sb.append("disabled=\"");
        sb.append(getDisabled());
        sb.append("\" ");
        sb.append("randomCode=\"");
        sb.append(getRandomCode());
        sb.append("\" ");
        sb.append("]");
        return sb.toString();
    }

}