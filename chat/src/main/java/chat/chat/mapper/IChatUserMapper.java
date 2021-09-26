package chat.chat.mapper;

import org.mapstruct.Mapper;

import chat.chat.model.dto.ChatUserDTO;
import chat.chat.model.entity.ChatUserEntity;

@Mapper(componentModel = "spring")
public interface IChatUserMapper {

    ChatUserDTO chatUserEntityToChatUserDto(ChatUserEntity chatUserEntity);

    ChatUserEntity chatUserDtoToChatUserEntity(ChatUserDTO chatUserDTO);
}
