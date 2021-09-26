package chat.chat.mapper;

import org.mapstruct.Mapper;

import chat.chat.model.dto.UserChatDTO;
import chat.chat.model.entity.UserChatEntity;

@Mapper(componentModel = "spring")
public interface IUserChatMapper {

    UserChatEntity userChatDtoToUserChatEntity(UserChatDTO userChatDTO);

    UserChatDTO userChatEntityToUserChatDto(UserChatEntity userChatEntity);
}
