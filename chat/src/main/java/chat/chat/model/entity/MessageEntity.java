package chat.chat.model.entity;

import org.springframework.data.annotation.Id;

public class MessageEntity {

    @Id
    private String id;
    private String userId;
    private String username;
    private String content;

    public MessageEntity() {
    }

    /**
     * @param userId
     * @param username
     * @param content
     */
    public MessageEntity(String userId, String username, String content) {
        this.userId = userId;
        this.username = username;
        this.content = content;
    }

    /**
     * @param id
     * @param userId
     * @param username
     * @param content
     */
    public MessageEntity(String id, String userId, String username, String content) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.content = content;
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
