package chat.chat.model.entity;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class MessageEntity {

    @Id
    private String id;

    private String userId;
    private String username;
    private String content;
    private String type;
    private Date time;
    private String originFilename;

    public MessageEntity() {}

    /**
     * @param userId
     * @param username
     * @param content
     */
    public MessageEntity(
        String userId,
        String username,
        String content,
        String type,
        Date time
    ) {
        this.id = new ObjectId().toString();
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.type = type;
        this.time = time;
    }

    /**
     * @param id
     * @param userId
     * @param username
     * @param content
     */
    public MessageEntity(
        String id,
        String userId,
        String username,
        String content,
        String type,
        Date time,
        String originFilename
    ) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.type = type;
        this.time = time;
        this.originFilename = originFilename;
    }

    /**
     * @return the originFilename
     */
    public String getOriginFilename() {
        return originFilename;
    }

    /**
     * @param originFilename the originFilename to set
     */
    public void setOriginFilename(String originFilename) {
        this.originFilename = originFilename;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
}
