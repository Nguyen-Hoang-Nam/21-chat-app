package chat.chat.model.request;

public class ChatCreateRequest {

    private String chatName;

    public ChatCreateRequest() {
    }

    /**
     * @param chatName
     */
    public ChatCreateRequest(String chatName) {
        this.chatName = chatName;
    }

    /**
     * @return the chatName
     */
    public String getChatName() {
        return chatName;
    }

    /**
     * @param chatName the chatName to set
     */
    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

}
