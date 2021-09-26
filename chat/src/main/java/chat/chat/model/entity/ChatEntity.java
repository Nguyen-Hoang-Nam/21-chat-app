package chat.chat.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
public class ChatEntity implements Serializable {

    @Id
    private String id;
    private String chatName;
    private ArrayList<MessageEntity> messageEntities;
    private ArrayList<ChatUserEntity> chatUserEntities;

    public ChatEntity() {
    }

    /**
     * @param chatName
     */
    public ChatEntity(String chatName) {
        this.chatName = chatName;
    }

    /**
     * @param id
     * @param chatName
     */
    public ChatEntity(String id, String chatName) {
        this.id = id;
        this.chatName = chatName;
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
     * @return the messageEntities
     */
    public ArrayList<MessageEntity> getMessageEntities() {
        return messageEntities;
    }

    /**
     * @param messageEntities the messageEntities to set
     */
    public void setMessageEntities(ArrayList<MessageEntity> messageEntities) {
        this.messageEntities = messageEntities;
    }

    /**
     * @return the chatUserEntities
     */
    public ArrayList<ChatUserEntity> getChatUserEntities() {
        return chatUserEntities;
    }

    /**
     * @param chatUserEntities the chatUserEntities to set
     */
    public void setChatUserEntities(ArrayList<ChatUserEntity> chatUserEntities) {
        this.chatUserEntities = chatUserEntities;
    }

}
