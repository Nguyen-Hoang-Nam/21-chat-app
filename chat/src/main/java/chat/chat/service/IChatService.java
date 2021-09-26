package chat.chat.service;

import java.util.List;

import chat.chat.model.dto.ChatDTO;
import chat.chat.model.dto.MessageDTO;

public interface IChatService {

    public void create(String token, ChatDTO chatDTO);

    public String addUserById(String chatId, String userId);

    public void addMessage(String chatId, MessageDTO messageDTO);

    public ChatDTO getChatById(String token, String chatId);

    public List<MessageDTO> getOldMessages(String token, String chatId);
}
