package chat.chat.service;

import chat.chat.model.dto.ChatDTO;
import chat.chat.model.dto.MessageDTO;
import java.util.List;

public interface IChatService {
    public void create(String token, ChatDTO chatDTO);

    public String addUserById(String token, String chatId, String userId);

    public void addMessage(String chatId, MessageDTO messageDTO);

    public ChatDTO getChatById(String token, String chatId);

    public List<MessageDTO> getOldMessages(String token, String chatId);
}
