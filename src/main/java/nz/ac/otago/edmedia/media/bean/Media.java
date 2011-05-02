package nz.ac.otago.edmedia.media.bean;

import nz.ac.otago.edmedia.spring.bean.WebID;
import nz.ac.otago.edmedia.util.CommonUtil;
import org.hibernate.search.annotations.*;

import java.util.HashSet;
import java.util.Set;


/**
 * Media bean. This bean has the following fields:
 * <p/>
 * <ul>
 * <li><b>id </b>- ID</li>
 * <li><b>title </b>- Title</li>
 * <li><b>description </b>- Description</li>
 * <li><b>uploadFile </b>- Upload File</li>
 * <li><b>realFilename </b>- Real File Name</li>
 * <li><b>thumbnail </b>- Thumbnail</li>
 * <li><b>mediaType </b>- Media Type(1: Other, 5: Image, 10: Audio, 20: Video)</li>
 * <li><b>convertTo </b>- convert to a different format</li>
 * <li><b>otherFormatFilename </b>- File name of other format</li>
 * <li><b>width </b>- Width</li>
 * <li><b>height </b>- Height</li>
 * <li><b>duration </b>- Video or Audio Duration (in ms), Or image number</li>
 * <li><b>snapshotPosition </b>- Snapshot Position</li>
 * <li><b>tags </b>- Tags</li>
 * <li><b>status </b>- Status(0:waiting, 1:processing, 2:finished, 9:unrecognized)</li>
 * <li><b>isOnOtherServer </b>- Put this media on other server?</li>
 * <li><b>accessType </b>- access type(0: public, 10: hidden, 20: private)</li>
 * <li><b>viaEmail </b>- Upload via Email?</li>
 * <li><b>viaMMS </b>- Upload via MMS?</li>
 * <li><b>uploadOnly </b>- Upload Only?</li>
 * <li><b>randomCode </b>- Random Code</li>
 * <li><b>locationCode </b>- Location Code(where to put media file)</li>
 * <li><b>uploadTime </b>- Upload Time</li>
 * <li><b>accessTimes </b>- Access Times</li>
 * <li><b>processTimes </b>- Process Times</li>
 * <li><b>user </b>- User</li>
 * <li><b>albumMedias </b>- All AlbumMedias</li>
 * <li><b>comments </b>- All User Comments</li>
 * <li><b>annotations </b>- All User Annotations</li>
 * <li><b>accessRules </b>- Access Rules</li>
 * </ul>
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
@Indexed
public class Media extends WebID {

    // --------------------------- VARIABLES START ----------------------------

    /**
     * ID
     */
    @DocumentId
    private Long id;

    /**
     * Title
     */
    @Field(index = Index.TOKENIZED, store = Store.NO)
    private String title;

    /**
     * Description
     */
    @Field(index = Index.TOKENIZED, store = Store.NO)
    private String description;

    /**
     * Upload File
     */
    private org.springframework.web.multipart.MultipartFile uploadFile;

    private String uploadFileUserName;

    /**
     * Real File Name
     */
    private String realFilename;

    /**
     * Thumbnail
     */
    private String thumbnail;

    /**
     * Media Type(1: Other, 5: Image, 10: Audio, 20: Video)
     */
    private int mediaType;

    /**
     * convert to a different format
     */
    private String convertTo;

    /**
     * File name of other format
     */
    private String otherFormatFilename;

    /**
     * Width
     */
    private int width;

    /**
     * Height
     */
    private int height;

    /**
     * Video or Audio Duration (in ms), Or image number
     */
    private int duration;

    /**
     * Snapshot Position
     */
    private int snapshotPosition;

    /**
     * Tags
     */
    @Field(index = Index.TOKENIZED, store = Store.NO)
    private String tags;

    /**
     * Status(0:waiting, 1:processing, 2:finished, 9:unrecognized)
     */
    private int status;

    /**
     * Put this media on other server?
     */
    private boolean isOnOtherServer;

    /**
     * access type(0: public, 10: hidden, 20: private)
     */
    private int accessType;

    /**
     * Upload via Email?
     */
    private boolean viaEmail;

    /**
     * Upload via MMS?
     */
    private boolean viaMMS;

    /**
     * Upload Only?
     */
    private boolean uploadOnly;

    /**
     * Random Code
     */
    private String randomCode;

    /**
     * Location Code(where to put media file)
     */
    private String locationCode;

    /**
     * Upload Time
     */
    private java.util.Date uploadTime;

    /**
     * Access Times
     */
    private long accessTimes;

    /**
     * Process Times
     */
    private int processTimes;

    /**
     * User
     */
    private User user;

    private Long userID;

    /**
     * All AlbumMedias
     */
    private Set<AlbumMedia> albumMedias = new HashSet<AlbumMedia>();
    private Long[] albumMediasID;

    /**
     * All User Comments
     */
    private Set<Comment> comments = new HashSet<Comment>();
    private Long[] commentsID;

    /**
     * All User Annotations
     */
    private Set<Annotation> annotations = new HashSet<Annotation>();
    private Long[] annotationsID;

    /**
     * Access Rules
     */
    private Set<AccessRule> accessRules = new HashSet<AccessRule>();
    private Long[] accessRulesID;


    // --------------------------- RELATIONSHIP MANAGEMENT --------------------
    /**
     * @param albumMedia to add
     */
    public void addAlbumMedias(AlbumMedia albumMedia) {
        albumMedia.setMedia(this);
        albumMedias.add(albumMedia);
    }

    /**
     * @param albumMedia to remove
     */
    public void removeAlbumMedias(AlbumMedia albumMedia) {
        albumMedia.setMedia(null);
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
     * @param comment to add
     */
    public void addComments(Comment comment) {
        comment.setMedia(this);
        comments.add(comment);
    }

    /**
     * @param comment to remove
     */
    public void removeComments(Comment comment) {
        comment.setMedia(null);
        comments.remove(comment);
    }

    /**
     * @return a new Comment
     */
    public Comment createComments() {
        Comment newComment = new Comment();
        addComments(newComment);
        return newComment;
    }
    /**
     * @param annotation to add
     */
    public void addAnnotations(Annotation annotation) {
        annotation.setMedia(this);
        annotations.add(annotation);
    }

    /**
     * @param annotation to remove
     */
    public void removeAnnotations(Annotation annotation) {
        annotation.setMedia(null);
        annotations.remove(annotation);
    }

    /**
     * @return a new Annotation
     */
    public Annotation createAnnotations() {
        Annotation newAnnotation = new Annotation();
        addAnnotations(newAnnotation);
        return newAnnotation;
    }
    /**
     * @param accessRule to add
     */
    public void addAccessRules(AccessRule accessRule) {
        accessRule.setMedia(this);
        accessRules.add(accessRule);
    }

    /**
     * @param accessRule to remove
     */
    public void removeAccessRules(AccessRule accessRule) {
        accessRule.setMedia(null);
        accessRules.remove(accessRule);
    }

    /**
     * @return a new AccessRule
     */
    public AccessRule createAccessRules() {
        AccessRule newAccessRule = new AccessRule();
        addAccessRules(newAccessRule);
        return newAccessRule;
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
     * Returns Title.
     *
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns Description.
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns Upload File.
     *
     * @return uploadFile
     */
    public org.springframework.web.multipart.MultipartFile getUploadFile() {
        return this.uploadFile;
    }

    public String getUploadFileUserName() {
        return this.uploadFileUserName;
    }

    /**
     * Returns Real File Name.
     *
     * @return realFilename
     */
    public String getRealFilename() {
        return this.realFilename;
    }

    /**
     * Returns Thumbnail.
     *
     * @return thumbnail
     */
    public String getThumbnail() {
        return this.thumbnail;
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
     * Returns convert to a different format.
     *
     * @return convertTo
     */
    public String getConvertTo() {
        return this.convertTo;
    }

    /**
     * Returns File name of other format.
     *
     * @return otherFormatFilename
     */
    public String getOtherFormatFilename() {
        return this.otherFormatFilename;
    }

    /**
     * Returns Width.
     *
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns Height.
     *
     * @return height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns Video or Audio Duration (in ms), Or image number.
     *
     * @return duration
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Returns Snapshot Position.
     *
     * @return snapshotPosition
     */
    public int getSnapshotPosition() {
        return this.snapshotPosition;
    }

    /**
     * Returns Tags.
     *
     * @return tags
     */
    public String getTags() {
        return this.tags;
    }

    /**
     * Returns Status(0:waiting, 1:processing, 2:finished, 9:unrecognized).
     *
     * @return status
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Returns Put this media on other server?.
     *
     * @return isOnOtherServer
     */
    public boolean getIsOnOtherServer() {
        return this.isOnOtherServer;
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
     * Returns Upload via Email?.
     *
     * @return viaEmail
     */
    public boolean getViaEmail() {
        return this.viaEmail;
    }

    /**
     * Returns Upload via MMS?.
     *
     * @return viaMMS
     */
    public boolean getViaMMS() {
        return this.viaMMS;
    }

    /**
     * Returns Upload Only?.
     *
     * @return uploadOnly
     */
    public boolean getUploadOnly() {
        return this.uploadOnly;
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
     * Returns Location Code(where to put media file).
     *
     * @return locationCode
     */
    public String getLocationCode() {
        return this.locationCode;
    }

    /**
     * Returns Upload Time.
     *
     * @return uploadTime
     */
    public java.util.Date getUploadTime() {
        return this.uploadTime;
    }

    public String getUploadTimePast() {
        return CommonUtil.timePast(this.uploadTime);
    }

    /**
     * Returns Access Times.
     *
     * @return accessTimes
     */
    public long getAccessTimes() {
        return this.accessTimes;
    }

    /**
     * Returns Process Times.
     *
     * @return processTimes
     */
    public int getProcessTimes() {
        return this.processTimes;
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
     * Returns All User Comments.
     *
     * @return comments
     */
    public Set<Comment> getComments() {
        return this.comments;
    }

    public Long[] getCommentsID() {
        return this.commentsID;
    }

    /**
     * Returns All User Annotations.
     *
     * @return annotations
     */
    public Set<Annotation> getAnnotations() {
        return this.annotations;
    }

    public Long[] getAnnotationsID() {
        return this.annotationsID;
    }

    /**
     * Returns Access Rules.
     *
     * @return accessRules
     */
    public Set<AccessRule> getAccessRules() {
        return this.accessRules;
    }

    public Long[] getAccessRulesID() {
        return this.accessRulesID;
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
     * Sets Title.
     *
     * @param title Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets Description.
     *
     * @param description Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets Upload File.
     *
     * @param uploadFile Upload File
     */
    public void setUploadFile(org.springframework.web.multipart.MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public void setUploadFileUserName(String uploadFileUserName) {
        this.uploadFileUserName = uploadFileUserName;
    }

    /**
     * Sets Real File Name.
     *
     * @param realFilename Real File Name
     */
    public void setRealFilename(String realFilename) {
        this.realFilename = realFilename;
    }

    /**
     * Sets Thumbnail.
     *
     * @param thumbnail Thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
     * Sets convert to a different format.
     *
     * @param convertTo convert to a different format
     */
    public void setConvertTo(String convertTo) {
        this.convertTo = convertTo;
    }

    /**
     * Sets File name of other format.
     *
     * @param otherFormatFilename File name of other format
     */
    public void setOtherFormatFilename(String otherFormatFilename) {
        this.otherFormatFilename = otherFormatFilename;
    }

    /**
     * Sets Width.
     *
     * @param width Width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets Height.
     *
     * @param height Height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets Video or Audio Duration (in ms), Or image number.
     *
     * @param duration Video or Audio Duration (in ms), Or image number
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Sets Snapshot Position.
     *
     * @param snapshotPosition Snapshot Position
     */
    public void setSnapshotPosition(int snapshotPosition) {
        this.snapshotPosition = snapshotPosition;
    }

    /**
     * Sets Tags.
     *
     * @param tags Tags
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * Sets Status(0:waiting, 1:processing, 2:finished, 9:unrecognized).
     *
     * @param status Status(0:waiting, 1:processing, 2:finished, 9:unrecognized)
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Sets Put this media on other server?.
     *
     * @param isOnOtherServer Put this media on other server?
     */
    public void setIsOnOtherServer(boolean isOnOtherServer) {
        this.isOnOtherServer = isOnOtherServer;
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
     * Sets Upload via Email?.
     *
     * @param viaEmail Upload via Email?
     */
    public void setViaEmail(boolean viaEmail) {
        this.viaEmail = viaEmail;
    }

    /**
     * Sets Upload via MMS?.
     *
     * @param viaMMS Upload via MMS?
     */
    public void setViaMMS(boolean viaMMS) {
        this.viaMMS = viaMMS;
    }

    /**
     * Sets Upload Only?.
     *
     * @param uploadOnly Upload Only?
     */
    public void setUploadOnly(boolean uploadOnly) {
        this.uploadOnly = uploadOnly;
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
     * Sets Location Code(where to put media file).
     *
     * @param locationCode Location Code(where to put media file)
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    /**
     * Sets Upload Time.
     *
     * @param uploadTime Upload Time
     */
    public void setUploadTime(java.util.Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * Sets Access Times.
     *
     * @param accessTimes Access Times
     */
    public void setAccessTimes(long accessTimes) {
        this.accessTimes = accessTimes;
    }

    /**
     * Sets Process Times.
     *
     * @param processTimes Process Times
     */
    public void setProcessTimes(int processTimes) {
        this.processTimes = processTimes;
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
     * Sets All User Comments.
     *
     * @param comments All User Comments
     */
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setCommentsID(Long[] commentsID) {
        this.commentsID = commentsID;
    }

    /**
     * Sets All User Annotations.
     *
     * @param annotations All User Annotations
     */
    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public void setAnnotationsID(Long[] annotationsID) {
        this.annotationsID = annotationsID;
    }

    /**
     * Sets Access Rules.
     *
     * @param accessRules Access Rules
     */
    public void setAccessRules(Set<AccessRule> accessRules) {
        this.accessRules = accessRules;
    }

    public void setAccessRulesID(Long[] accessRulesID) {
        this.accessRulesID = accessRulesID;
    }


    /**
     * @return a short name which should meaningfully identify this row to the user
     */
    public String getMeaningfulName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTitle());
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
        sb.append("title=\"");
        sb.append(getTitle());
        sb.append("\" ");
        sb.append("description=\"");
        sb.append(getDescription());
        sb.append("\" ");
        sb.append("uploadFile=\"");
        sb.append(getUploadFile());
        sb.append("\" ");
        sb.append("realFilename=\"");
        sb.append(getRealFilename());
        sb.append("\" ");
        sb.append("thumbnail=\"");
        sb.append(getThumbnail());
        sb.append("\" ");
        sb.append("mediaType=\"");
        sb.append(getMediaType());
        sb.append("\" ");
        sb.append("convertTo=\"");
        sb.append(getConvertTo());
        sb.append("\" ");
        sb.append("otherFormatFilename=\"");
        sb.append(getOtherFormatFilename());
        sb.append("\" ");
        sb.append("width=\"");
        sb.append(getWidth());
        sb.append("\" ");
        sb.append("height=\"");
        sb.append(getHeight());
        sb.append("\" ");
        sb.append("duration=\"");
        sb.append(getDuration());
        sb.append("\" ");
        sb.append("snapshotPosition=\"");
        sb.append(getSnapshotPosition());
        sb.append("\" ");
        sb.append("tags=\"");
        sb.append(getTags());
        sb.append("\" ");
        sb.append("status=\"");
        sb.append(getStatus());
        sb.append("\" ");
        sb.append("isOnOtherServer=\"");
        sb.append(getIsOnOtherServer());
        sb.append("\" ");
        sb.append("accessType=\"");
        sb.append(getAccessType());
        sb.append("\" ");
        sb.append("viaEmail=\"");
        sb.append(getViaEmail());
        sb.append("\" ");
        sb.append("viaMMS=\"");
        sb.append(getViaMMS());
        sb.append("\" ");
        sb.append("uploadOnly=\"");
        sb.append(getUploadOnly());
        sb.append("\" ");
        sb.append("randomCode=\"");
        sb.append(getRandomCode());
        sb.append("\" ");
        sb.append("locationCode=\"");
        sb.append(getLocationCode());
        sb.append("\" ");
        sb.append("uploadTime=\"");
        sb.append(getUploadTime());
        sb.append("\" ");
        sb.append("accessTimes=\"");
        sb.append(getAccessTimes());
        sb.append("\" ");
        sb.append("processTimes=\"");
        sb.append(getProcessTimes());
        sb.append("\" ");
        sb.append("]");
        return sb.toString();
    }

}