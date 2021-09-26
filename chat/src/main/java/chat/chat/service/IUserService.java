package chat.chat.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import chat.chat.model.dto.UserDTO;

public interface IUserService extends UserDetailsService {
    public void insert(UserDTO userDTO);

    public UserDTO getUserById(String id);

    public UserDTO getUserByUsername(String username);

    public UserDTO getUserFromToken(String token);
}
