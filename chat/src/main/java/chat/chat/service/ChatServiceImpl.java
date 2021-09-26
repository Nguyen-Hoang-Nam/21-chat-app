package chat.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import chat.chat.mapper.IChatMapper;
import chat.chat.mapper.IMessageMapper;
import chat.chat.model.dto.ChatDTO;
import chat.chat.model.dto.MessageDTO;
// import chat.chat.model.dto.UserChatDTO;
import chat.chat.model.entity.ChatEntity;
import chat.chat.model.entity.ChatUserEntity;
import chat.chat.model.entity.MessageEntity;
import chat.chat.model.entity.UserChatEntity;
import chat.chat.model.entity.UserEntity;
import chat.chat.repository.IChatRepository;
import chat.chat.repository.IUserRepository;
import chat.chat.util.JwtUtil;

@Service
public class ChatServiceImpl implements IChatService {
    IChatRepository chatRepository;

    IUserRepository userRepository;

    IMessageMapper messageMapper;

    IChatMapper chatMapper;

    private JwtUtil jwtUtil;

    /**
     * @param chatRepository
     * @param mapper
     */
    public ChatServiceImpl(IChatRepository chatRepository, IUserRepository userRepository, IChatMapper chatMapper,
            IMessageMapper messageMapper, JwtUtil jwtUtil) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatMapper = chatMapper;
        this.messageMapper = messageMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void create(String token, ChatDTO chatDTO) {
        ChatEntity chatEntity = chatMapper.chatDtoToChatEntity(chatDTO);

        String userId = jwtUtil.parseToken(token);
        UserEntity userEntity = userRepository.findUserById(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException(userId);
        }

        ArrayList<ChatUserEntity> chatUserEntities = new ArrayList<ChatUserEntity>();
        ChatUserEntity chatUserEntity = new ChatUserEntity(userId, userEntity.getUsername());

        chatUserEntities.add(chatUserEntity);

        chatEntity.setChatUserEntities(chatUserEntities);
        ChatEntity newChatEntity = chatRepository.save(chatEntity);
        String newChatId = newChatEntity.getId();

        userEntity.setLastChatId(newChatId);

        UserChatEntity userChatEntity = new UserChatEntity(newChatId, chatEntity.getChatName());
        ArrayList<UserChatEntity> userChatEntities = userEntity.getUserChatEntities();

        if (userChatEntities == null) {
            userChatEntities = new ArrayList<UserChatEntity>();
        }

        userChatEntities.add(userChatEntity);
        userEntity.setUserChatEntities(userChatEntities);

        userRepository.save(userEntity);
    }

    @Override
    public String addUserById(String chatId, String userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            throw new IllegalStateException("User not found");
        }

        // Add user to chat
        ChatEntity chatEntity = chatRepository.findById(chatId).orElse(null);

        if (chatEntity == null) {
            throw new IllegalStateException("Chat room not found");
        }

        ChatUserEntity chatUserEntity = new ChatUserEntity(userId, userEntity.getUsername());

        ArrayList<ChatUserEntity> chatUserEntities = chatEntity.getChatUserEntities();

        if (chatUserEntities == null) {
            chatUserEntities = new ArrayList<ChatUserEntity>();
        }

        chatUserEntities.add(chatUserEntity);
        chatEntity.setChatUserEntities(chatUserEntities);

        chatRepository.save(chatEntity);

        // Add chat to user
        userEntity.setLastChatId(chatId);

        UserChatEntity userChatEntity = new UserChatEntity(chatId, chatEntity.getChatName());
        ArrayList<UserChatEntity> userChatEntities = userEntity.getUserChatEntities();

        if (userChatEntities == null) {
            userChatEntities = new ArrayList<UserChatEntity>();
        }

        userChatEntities.add(userChatEntity);
        userEntity.setUserChatEntities(userChatEntities);

        userRepository.save(userEntity);

        return userEntity.getUsername();
    }

    public List<MessageDTO> getOldMessages(String token, String chatId) {
        String userId = jwtUtil.parseToken(token);

        ChatEntity chatEntity = chatRepository.findById(chatId).orElse(null);

        if (chatEntity == null) {
            throw new IllegalStateException("Chat room not found");
        }

        List<ChatUserEntity> chatUserEntities = chatEntity.getChatUserEntities();

        Boolean foundUser = false;
        for (ChatUserEntity chatUserEntity : chatUserEntities) {

            if (chatUserEntity.getId().equals(userId)) {
                foundUser = true;
                break;
            }
        }

        if (!foundUser) {
            throw new IllegalStateException("User not found in chatroom");
        }

        List<MessageEntity> messageEntities = chatEntity.getMessageEntities();
        List<MessageDTO> messageDTOs = messageMapper.messageEntitiesToMessageDtos(messageEntities);

        return messageDTOs;
    }

    @Override
    public ChatDTO getChatById(String token, String chatId) {
        String userId = jwtUtil.parseToken(token);

        ChatEntity chatEntity = chatRepository.findById(chatId).orElse(null);

        if (chatEntity == null) {
            throw new IllegalStateException("Chat room not found");
        }

        List<ChatUserEntity> chatUserEntities = chatEntity.getChatUserEntities();

        Boolean foundUser = false;
        for (ChatUserEntity chatUserEntity : chatUserEntities) {

            if (chatUserEntity.getId().equals(userId)) {
                foundUser = true;
                break;
            }
        }

        if (!foundUser) {
            throw new IllegalStateException("User not found in chatroom");
        }

        ChatDTO chatDTO = chatMapper.chatEntityToChatDto(chatEntity);
        return chatDTO;
    }

    @Override
    public void addMessage(String chatId, MessageDTO messageDTO) {
        ChatEntity chatEntity = chatRepository.findById(chatId).orElse(null);
        if (chatEntity == null) {
            throw new IllegalStateException("Chat room not found");
        }

        MessageEntity messageEntity = messageMapper.messageDtoToMessageEntity(messageDTO);

        ArrayList<MessageEntity> messageEntities = chatEntity.getMessageEntities();
        if (messageEntities == null) {
            messageEntities = new ArrayList<MessageEntity>();
        }

        messageEntities.add(messageEntity);
        chatEntity.setMessageEntities(messageEntities);

        chatRepository.save(chatEntity);
    }
}
