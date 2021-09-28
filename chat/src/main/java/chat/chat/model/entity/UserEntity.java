package chat.chat.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class UserEntity implements Serializable {

    @Id
    private String id;

    private String username;
    private String password;
    private ArrayList<UserChatEntity> userChatEntities;

    private ArrayList<String> chatroomOrder;

    public UserEntity() {}

    /**
     * @param username
     * @param password
     */
    public UserEntity(String username, String password) {
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
     * @param id
     * @param username
     * @param password
     */
    public UserEntity(String id, String username, String password) {
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

    /**
     * @return the userChatEntities
     */
    public ArrayList<UserChatEntity> getUserChatEntities() {
        return userChatEntities;
    }

    /**
     * @param userChatEntities the userChatEntities to set
     */
    public void setUserChatEntities(
        ArrayList<UserChatEntity> userChatEntities
    ) {
        this.userChatEntities = userChatEntities;
    }
}
