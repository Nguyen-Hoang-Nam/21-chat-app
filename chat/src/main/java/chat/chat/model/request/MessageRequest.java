package chat.chat.model.request;

public class MessageRequest {

    private String userId;
    private String username;
    private String content;

    public MessageRequest() {
    }

    /**
     * @param userId
     * @param username
     * @param content
     */
    public MessageRequest(String userId, String username, String content) {
        this.userId = userId;
        this.username = username;
        this.content = content;
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
