package chat.chat.model.dto;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.springframework.data.annotation.Id;

public class UserDTO implements Serializable {

    @Id
    private String id;
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    private ArrayList<UserChatDTO> userChatDTOs;
    private String lastChatId;

    public UserDTO() {
    }

    /**
     * @param username
     * @param password
     */
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
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
     * @return the lastChatId
     */
    public String getLastChatId() {
        return lastChatId;
    }

    /**
     * @param lastChatId the lastChatId to set
     */
    public void setLastChatId(String lastChatId) {
        this.lastChatId = lastChatId;
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
