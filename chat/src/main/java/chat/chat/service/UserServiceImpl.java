package chat.chat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import chat.chat.mapper.IUserMapper;
import chat.chat.model.dto.UserDTO;
import chat.chat.model.entity.UserEntity;
import chat.chat.repository.IUserRepository;
import chat.chat.util.JwtUtil;

@Service
public class UserServiceImpl implements IUserService {

    private IUserRepository userRepository;

    private IUserMapper mapper;

    BCryptPasswordEncoder encoder;

    private JwtUtil jwtUtil;

    /**
     * @param userRepository
     * @param mapper
     * @param encoder
     * @param jwtUtil
     */
    public UserServiceImpl(IUserRepository userRepository, IUserMapper mapper, BCryptPasswordEncoder encoder,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public void insert(UserDTO userDTO) {
        userDTO.setId(UUID.randomUUID().toString());

        UserEntity userEntity = mapper.userDtoTouserEntity(userDTO);
        userEntity.setPassword(encoder.encode(userDTO.getPassword()));

        userRepository.save(userEntity);
    }

    public UserDTO getUserById(String id) {
        UserEntity userEntity = userRepository.findUserById(id);

        if (userEntity == null) {
            throw new UsernameNotFoundException(id);
        }

        UserDTO userDTO = mapper.userEntityToUserDto(userEntity);

        return userDTO;
    }

    public UserDTO getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findUserByUsername(username);

        UserDTO userDTO = mapper.userEntityToUserDto(userEntity);

        return userDTO;
    }

    public UserDTO getUserFromToken(String token) {
        String userId = jwtUtil.parseToken(token);

        return getUserById(userId);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByUsername(username);

        return new User(username, userEntity.getPassword(), true, true, true, true, new ArrayList<GrantedAuthority>());
    }
}
