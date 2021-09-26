package chat.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import chat.chat.model.dto.ChatDTO;
import chat.chat.model.dto.MessageDTO;
import chat.chat.model.entity.ChatEntity;
import chat.chat.model.entity.MessageEntity;
import chat.chat.model.request.ChatCreateRequest;
import chat.chat.model.request.MessageRequest;

@Mapper(componentModel = "spring", uses = { IMessageMapper.class, IChatUserMapper.class })
public interface IChatMapper {

    @Mappings({ @Mapping(source = "chatDTO.messageDTOs", target = "messageEntities"),
            @Mapping(source = "chatDTO.chatUserDTOs", target = "chatUserEntities"), })
    ChatEntity chatDtoToChatEntity(ChatDTO chatDTO);

    @Mappings({ @Mapping(source = "chatEntity.messageEntities", target = "messageDTOs"),
            @Mapping(source = "chatEntity.chatUserEntities", target = "chatUserDTOs"), })
    ChatDTO chatEntityToChatDto(ChatEntity chatEntity);

    ChatDTO chatCreateRequestToChatDto(ChatCreateRequest chatCreateRequest);

    MessageDTO messageRequestToMessageDto(MessageRequest messageRequest);

    MessageEntity messageDtoToMessageEntity(MessageDTO messageDTO);
}
