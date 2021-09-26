package chat.chat.model.entity;

import org.springframework.data.annotation.Id;

public class UserChatEntity {

    @Id
    private String id;
    private String chatName;
    private String lastMessage;
    private String lastUsername;

    public UserChatEntity() {
    }

    /**
     * @param id
     * @param chatName
     */
    public UserChatEntity(String id, String chatName) {
        this.id = id;
        this.chatName = chatName;
    }

    /**
     * @param id
     * @param chatName
     * @param lastMessage
     * @param lastUsername
     */
    public UserChatEntity(String id, String chatName, String lastMessage, String lastUsername) {
        this.id = id;
        this.chatName = chatName;
        this.lastMessage = lastMessage;
        this.lastUsername = lastUsername;
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

    /**
     * @return the lastMessage
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * @param lastMessage the lastMessage to set
     */
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    /**
     * @return the lastUsername
     */
    public String getLastUsername() {
        return lastUsername;
    }

    /**
     * @param lastUsername the lastUsername to set
     */
    public void setLastUsername(String lastUsername) {
        this.lastUsername = lastUsername;
    }

}
