package nz.ac.otago.edmedia.media.bean;

import nz.ac.otago.edmedia.util.CommonUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * Comment bean. This bean has the following fields:
 * <p/>
 * <ul>
 * <li><b>id </b>- ID</li>
 * <li><b>msg </b>- Message</li>
 * <li><b>msgTime </b>- Message Time</li>
 * <li><b>credits </b>- Comment Credits</li>
 * <li><b>media </b>- Media</li>
 * <li><b>author </b>- Author</li>
 * <li><b>comment </b>- Parent Comment</li>
 * <li><b>childComments </b>- Child Comments</li>
 * </ul>
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 */
public class Comment {

    // --------------------------- VARIABLES START ----------------------------

    /**
     * ID
     */
    private Long id;

    /**
     * Message
     */
    private String msg;

    /**
     * Message Time
     */
    private java.util.Date msgTime;

    /**
     * Comment Credits
     */
    private int credits;

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

    /**
     * Parent Comment
     */
    private Comment comment;

    private Long commentID;

    /**
     * Child Comments
     */
    private Set<Comment> childComments = new HashSet<Comment>();
    private Long[] childCommentsID;


    // --------------------------- RELATIONSHIP MANAGEMENT --------------------
    /**
     * @param comment to add
     */
    public void addChildComments(Comment comment) {
        comment.setComment(this);
        childComments.add(comment);
    }

    /**
     * @param comment to remove
     */
    public void removeChildComments(Comment comment) {
        comment.setComment(null);
        childComments.remove(comment);
    }

    /**
     * @return a new Comment
     */
    public Comment createChildComments() {
        Comment newComment = new Comment();
        addChildComments(newComment);
        return newComment;
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
     * Returns Message.
     *
     * @return msg
     */
    public String getMsg() {
        return this.msg;
    }

    /**
     * Returns Message Time.
     *
     * @return msgTime
     */
    public java.util.Date getMsgTime() {
        return this.msgTime;
    }

    public String getMsgTimePast() {
        return CommonUtil.timePast(this.msgTime);
    }

    /**
     * Returns Comment Credits.
     *
     * @return credits
     */
    public int getCredits() {
        return this.credits;
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

    /**
     * Returns Parent Comment.
     *
     * @return comment
     */
    public Comment getComment() {
        return this.comment;
    }

    public Long getCommentID() {
        return this.commentID;
    }

    /**
     * Returns Child Comments.
     *
     * @return childComments
     */
    public Set<Comment> getChildComments() {
        return this.childComments;
    }

    public Long[] getChildCommentsID() {
        return this.childCommentsID;
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
     * Sets Message.
     *
     * @param msg Message
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Sets Message Time.
     *
     * @param msgTime Message Time
     */
    public void setMsgTime(java.util.Date msgTime) {
        this.msgTime = msgTime;
    }

    /**
     * Sets Comment Credits.
     *
     * @param credits Comment Credits
     */
    public void setCredits(int credits) {
        this.credits = credits;
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
     * Sets Parent Comment.
     *
     * @param comment Parent Comment
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setCommentID(Long commentID) {
        this.commentID = commentID;
    }

    /**
     * Sets Child Comments.
     *
     * @param childComments Child Comments
     */
    public void setChildComments(Set<Comment> childComments) {
        this.childComments = childComments;
    }

    public void setChildCommentsID(Long[] childCommentsID) {
        this.childCommentsID = childCommentsID;
    }


    /**
     * @return a short name which should meaningfully identify this row to the user
     */
    public String getMeaningfulName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getMsg());
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
        sb.append("msg=\"");
        sb.append(getMsg());
        sb.append("\" ");
        sb.append("msgTime=\"");
        sb.append(getMsgTime());
        sb.append("\" ");
        sb.append("credits=\"");
        sb.append(getCredits());
        sb.append("\" ");
        sb.append("]");
        return sb.toString();
    }

}