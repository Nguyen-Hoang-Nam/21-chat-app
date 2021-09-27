package chat.chat.service;

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
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements IChatService {

    private IChatRepository chatRepository;

    private IUserRepository userRepository;

    private IMessageMapper messageMapper;

    private IChatMapper chatMapper;

    private JwtUtil jwtUtil;

    /**
     * @param chatRepository
     * @param mapper
     */
    public ChatServiceImpl(
        IChatRepository chatRepository,
        IUserRepository userRepository,
        IChatMapper chatMapper,
        IMessageMapper messageMapper,
        JwtUtil jwtUtil
    ) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatMapper = chatMapper;
        this.messageMapper = messageMapper;
        this.jwtUtil = jwtUtil;
    }

    private Boolean checkUserInChatroom(
        ArrayList<ChatUserEntity> chatUserEntities,
        String userId
    ) {
        for (ChatUserEntity chatUserEntity : chatUserEntities) {
            if (chatUserEntity.getId().equals(userId)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String create(String token, ChatDTO chatDTO) {
        ChatEntity chatEntity = chatMapper.chatDtoToChatEntity(chatDTO);

        String userId = jwtUtil.parseToken(token);
        UserEntity userEntity = userRepository.findUserById(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException(userId);
        }

        ArrayList<ChatUserEntity> chatUserEntities = new ArrayList<ChatUserEntity>();
        ChatUserEntity chatUserEntity = new ChatUserEntity(
            userId,
            userEntity.getUsername()
        );

        chatUserEntities.add(chatUserEntity);

        chatEntity.setChatUserEntities(chatUserEntities);
        ChatEntity newChatEntity = chatRepository.save(chatEntity);
        String newChatId = newChatEntity.getId();

        userEntity.setLastChatId(newChatId);

        UserChatEntity userChatEntity = new UserChatEntity(
            newChatId,
            chatEntity.getChatName()
        );
        ArrayList<UserChatEntity> userChatEntities = userEntity.getUserChatEntities();

        if (userChatEntities == null) {
            userChatEntities = new ArrayList<UserChatEntity>();
        }

        userChatEntities.add(userChatEntity);
        userEntity.setUserChatEntities(userChatEntities);

        userRepository.save(userEntity);

        return newChatId;
    }

    @Override
    public String addUserById(String token, String chatId, String userId) {
        // Check current user existed
        String currentUserId = jwtUtil.parseToken(token);
        UserEntity currentUserEntity = userRepository.findUserById(userId);
        if (currentUserEntity == null) {
            throw new UsernameNotFoundException(currentUserId);
        }

        // Get current chatroom
        ChatEntity chatEntity = chatRepository.findById(chatId).orElse(null);
        if (chatEntity == null) {
            throw new IllegalStateException("Chat room not found");
        }

        // Check current user in this chatroom
        ArrayList<ChatUserEntity> chatUserEntities = chatEntity.getChatUserEntities();

        Boolean foundCurrentUser = checkUserInChatroom(
            chatUserEntities,
            currentUserId
        );
        if (!foundCurrentUser) {
            throw new IllegalStateException("User not found in chatroom");
        }

        // Check new user existed
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            throw new IllegalStateException("User not found");
        }

        // Check current user in this chatroom
        Boolean foundNewUser = checkUserInChatroom(chatUserEntities, userId);
        if (foundNewUser) {
            throw new IllegalStateException("New user found in chatroom");
        }

        // Add new user to chatroom
        chatUserEntities = chatEntity.getChatUserEntities();
        if (chatUserEntities == null) {
            chatUserEntities = new ArrayList<ChatUserEntity>();
        }

        ChatUserEntity chatUserEntity = new ChatUserEntity(
            userId,
            userEntity.getUsername()
        );

        chatUserEntities.add(chatUserEntity);
        chatEntity.setChatUserEntities(chatUserEntities);

        chatRepository.save(chatEntity);

        // Add chatroom to new user
        ArrayList<UserChatEntity> userChatEntities = userEntity.getUserChatEntities();
        if (userChatEntities == null) {
            userChatEntities = new ArrayList<UserChatEntity>();
        }

        UserChatEntity userChatEntity = new UserChatEntity(
            chatId,
            chatEntity.getChatName()
        );

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

        ArrayList<ChatUserEntity> chatUserEntities = chatEntity.getChatUserEntities();

        Boolean foundUser = checkUserInChatroom(chatUserEntities, userId);
        if (!foundUser) {
            throw new IllegalStateException("User not found in chatroom");
        }

        List<MessageEntity> messageEntities = chatEntity.getMessageEntities();
        List<MessageDTO> messageDTOs = messageMapper.messageEntitiesToMessageDtos(
            messageEntities
        );

        return messageDTOs;
    }

    @Override
    public ChatDTO getChatById(String token, String chatId) {
        String userId = jwtUtil.parseToken(token);

        ChatEntity chatEntity = chatRepository.findById(chatId).orElse(null);
        if (chatEntity == null) {
            throw new IllegalStateException("Chat room not found");
        }

        ArrayList<ChatUserEntity> chatUserEntities = chatEntity.getChatUserEntities();

        Boolean foundUser = checkUserInChatroom(chatUserEntities, userId);
        if (!foundUser) {
            throw new IllegalStateException("User not found in chatroom");
        }

        ChatDTO chatDTO = chatMapper.chatEntityToChatDto(chatEntity);
        return chatDTO;
    }

    @Override
    public MessageDTO addMessage(String chatId, MessageDTO messageDTO) {
        ChatEntity chatEntity = chatRepository.findById(chatId).orElse(null);
        if (chatEntity == null) {
            throw new IllegalStateException("Chat room not found");
        }

        ArrayList<MessageEntity> messageEntities = chatEntity.getMessageEntities();
        if (messageEntities == null) {
            messageEntities = new ArrayList<MessageEntity>();
        }

        MessageEntity messageEntity = new MessageEntity(
            messageDTO.getUserId(),
            messageDTO.getUsername(),
            messageDTO.getContent()
        );

        messageEntities.add(messageEntity);
        chatEntity.setMessageEntities(messageEntities);
        chatRepository.save(chatEntity);

        MessageDTO newMessageDTO = messageMapper.messageEntityToMessageDto(
            messageEntity
        );
        return newMessageDTO;
    }
}
