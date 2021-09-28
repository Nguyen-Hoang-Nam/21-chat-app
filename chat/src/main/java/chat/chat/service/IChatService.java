package chat.chat.service;

import chat.chat.model.dto.ChatDTO;
import chat.chat.model.dto.MessageDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IChatService {
    public String create(String token, ChatDTO chatDTO);

    public String addUserById(String token, String chatId, String userId);

    public MessageDTO addMessage(String chatId, MessageDTO messageDTO);

    public ChatDTO getChatById(String token, String chatId);

    public List<MessageDTO> getOldMessages(String token, String chatId);

    public MessageDTO addMessageFile(
        String token,
        MultipartFile file,
        String chatId
    );
}
