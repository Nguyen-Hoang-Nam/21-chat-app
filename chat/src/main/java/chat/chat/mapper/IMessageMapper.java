package chat.chat.mapper;

import chat.chat.model.dto.MessageDTO;
import chat.chat.model.entity.MessageEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IMessageMapper {
    MessageDTO messageEntityToMessageDto(MessageEntity messageEntity);

    List<MessageDTO> messageEntitiesToMessageDtos(
        List<MessageEntity> messageEntities
    );

    List<MessageEntity> messageDtosToMessageEntity(
        List<MessageDTO> messageDTOs
    );

    MessageEntity messageDtoToMessageEntity(MessageDTO messageDTO);
}
