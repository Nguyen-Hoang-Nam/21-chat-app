package chat.chat.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.data.annotation.Id;

public class UserDTO implements Serializable {

    @Id
    private String id;

    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    private ArrayList<UserChatDTO> userChatDTOs;

    private ArrayList<String> chatroomOrder;

    public UserDTO() {}

    /**
     * @param username
     * @param password
     */
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return the chatroomOrder
     */
    public ArrayList<String> getChatroomOrder() {
        return chatroomOrder;
    }

    /**
     * @param chatroomOrder the chatroomOrder to set
     */
    public void setChatroomOrder(ArrayList<String> chatroomOrder) {
        this.chatroomOrder = chatroomOrder;
    }

    /**
     * @return the userChatDTOs
     */
    public ArrayList<UserChatDTO> getUserChatDTOs() {
        return userChatDTOs;
    }

    /**
     * @param userChatDTOs the userChatDTOs to set
     */
    public void setUserChatDTOs(ArrayList<UserChatDTO> userChatDTOs) {
        this.userChatDTOs = userChatDTOs;
    }

    /**
     * @param id
     * @param username
     * @param password
     */
    public UserDTO(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
