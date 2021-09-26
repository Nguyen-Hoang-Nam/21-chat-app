package chat.chat.model.dto;

import java.util.ArrayList;

public class ChatDTO {

    private String id;
    private String chatName;
    private ArrayList<MessageDTO> messageDTOs;
    private ArrayList<ChatUserDTO> chatUserDTOs;

    public ChatDTO() {
    }

    /**
     * @param chatName
     */
    public ChatDTO(String chatName) {
        this.chatName = chatName;
    }

    /**
     * @param id
     * @param chatName
     */
    public ChatDTO(String id, String chatName) {
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
     * @return the messageDTOs
     */
    public ArrayList<MessageDTO> getMessageDTOs() {
        return messageDTOs;
    }

    /**
     * @param messageDTOs the messageDTOs to set
     */
    public void setMessageDTOs(ArrayList<MessageDTO> messageDTOs) {
        this.messageDTOs = messageDTOs;
    }

    /**
     * @return the chatUserDTOs
     */
    public ArrayList<ChatUserDTO> getChatUserDTOs() {
        return chatUserDTOs;
    }

    /**
     * @param chatUserDTOs the chatUserDTOs to set
     */
    public void setChatUserDTOs(ArrayList<ChatUserDTO> chatUserDTOs) {
        this.chatUserDTOs = chatUserDTOs;
    }

}
