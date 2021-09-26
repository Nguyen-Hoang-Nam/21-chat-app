package chat.chat.model.entity;

import org.springframework.data.annotation.Id;

public class ChatUserEntity {

    @Id
    private String id;
    private String username;

    public ChatUserEntity() {
    }

    /**
     * @param id
     * @param username
     */
    public ChatUserEntity(String id, String username) {
        this.id = id;
        this.username = username;
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

}
