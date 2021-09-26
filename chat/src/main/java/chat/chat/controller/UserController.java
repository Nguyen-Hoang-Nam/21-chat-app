package chat.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chat.chat.mapper.IUserMapper;
import chat.chat.model.dto.UserDTO;
import chat.chat.model.request.UserSignUpRequest;
import chat.chat.service.IUserService;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins = "http://127.0.01:3000")
public class UserController {

    private IUserService service;
    private IUserMapper mapper;

    /**
     * @param service
     * @param mapper
     */
    public UserController(IUserService service, IUserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public void insert(@RequestBody UserSignUpRequest userSignUpRequest) {
        UserDTO userDTO = mapper.userSignUpRequestToUserDto(userSignUpRequest);

        service.insert(userDTO);
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser(@RequestHeader("Authorization") String requestToken) {
        String token = "";

        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);
        }

        UserDTO userDTO = service.getUserFromToken(token);

        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }
}
