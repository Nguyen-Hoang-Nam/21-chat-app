package chat.chat.model.request;

public class ChatAddUserRequest {

    private String userId;

    public ChatAddUserRequest() {
    }

    /**
     * @param userId
     */
    public ChatAddUserRequest(String userId) {
        this.userId = userId;
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

}
