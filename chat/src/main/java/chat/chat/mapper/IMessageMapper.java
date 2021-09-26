package chat.chat.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import chat.chat.model.dto.MessageDTO;
import chat.chat.model.entity.MessageEntity;

@Mapper(componentModel = "spring")
public interface IMessageMapper {

    MessageDTO messageEntityToMessageDto(MessageEntity messageEntity);

    List<MessageDTO> messageEntitiesToMessageDtos(List<MessageEntity> messageEntities);

    MessageEntity messageDtoToMessageEntity(MessageDTO messageDTO);
}
