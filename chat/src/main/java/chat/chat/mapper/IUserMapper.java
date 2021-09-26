package chat.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import chat.chat.model.dto.UserDTO;
import chat.chat.model.entity.UserEntity;
import chat.chat.model.request.UserSignUpRequest;

@Mapper(componentModel = "spring", uses = { IUserChatMapper.class })
public interface IUserMapper {

    @Mapping(source = "userDTO.userChatDTOs", target = "userChatEntities")
    UserEntity userDtoTouserEntity(UserDTO userDTO);

    @Mapping(source = "userEntity.userChatEntities", target = "userChatDTOs")
    UserDTO userEntityToUserDto(UserEntity userEntity);

    UserDTO userSignUpRequestToUserDto(UserSignUpRequest userSignUpRequest);
}
