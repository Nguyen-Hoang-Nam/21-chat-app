package chat.chat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
// import org.springframework.messaging.simp.annotation.SubscribeMapping;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import chat.chat.mapper.IChatMapper;
import chat.chat.model.dto.ChatDTO;
import chat.chat.model.dto.MessageDTO;
import chat.chat.model.request.ChatAddUserRequest;
import chat.chat.model.request.ChatCreateRequest;
import chat.chat.model.request.MessageRequest;
import chat.chat.service.IChatService;

@RestController
@RequestMapping(path = "/chat")
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class ChatController {

    private IChatService service;

    private IChatMapper mapper;

    private SimpMessagingTemplate template;

    /**
     * @param service
     * @param mapper
     */
    public ChatController(IChatService service, IChatMapper mapper, SimpMessagingTemplate template) {
        this.service = service;
        this.mapper = mapper;
        this.template = template;
    }

    @PostMapping(path = "/create")
    public void create(@RequestHeader("Authorization") String requestToken,
            @RequestBody ChatCreateRequest chatCreateRequest) {

        String token = "";

        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);
        }

        ChatDTO chatDTO = mapper.chatCreateRequestToChatDto(chatCreateRequest);

        service.create(token, chatDTO);
    }

    @PostMapping(path = "/add-user/{chatId}")
    public ResponseEntity<String> addUserById(@PathVariable String chatId,
            @RequestBody ChatAddUserRequest chatAddUserRequest) {
        String userId = chatAddUserRequest.getUserId();
        return new ResponseEntity<String>(service.addUserById(chatId, userId), HttpStatus.OK);
    }

    @GetMapping(path = "/get-chat/{chatId}")
    public ResponseEntity<ChatDTO> getChatById(@RequestHeader("Authorization") String requestToken,
            @PathVariable String chatId) {
        String token = "";

        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);
        }

        ChatDTO chatDTO = service.getChatById(token, chatId);
        return new ResponseEntity<ChatDTO>(chatDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/old-message/{chatId}")
    public ResponseEntity<List<MessageDTO>> getOldMessages(@RequestHeader("Authorization") String requestToken,
            @PathVariable String chatId) {
        String token = "";

        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);
        }

        List<MessageDTO> messageDTOs = service.getOldMessages(token, chatId);
        return new ResponseEntity<List<MessageDTO>>(messageDTOs, HttpStatus.OK);
    }

    @MessageMapping("/message/{chatId}")
    public void sendMessage(@DestinationVariable String chatId, MessageRequest messageRequest) {
        MessageDTO messageDTO = mapper.messageRequestToMessageDto(messageRequest);

        service.addMessage(chatId, messageDTO);

        template.convertAndSend("/topic/message." + chatId, messageDTO);
    }

    // @SubscribeMapping("/connected.users")
    // public String newConnected(SimpMessageHeaderAccessor headerAccessor) {
    // return headerAccessor.getSessionAttributes().get("username").toString();
    // }
}
