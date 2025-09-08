package ma.ensa.backendspringboot.controller;

import lombok.RequiredArgsConstructor;
import ma.ensa.backendspringboot.dto.ChatRequest;
import ma.ensa.backendspringboot.dto.MessageResponse;
import ma.ensa.backendspringboot.model.Message;
import ma.ensa.backendspringboot.service.MessageService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conversations/{conversationId}/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public MessageResponse sendMessage(
            @PathVariable Long conversationId,
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        String message = request.get("message");
        return messageService.addUserMessage(conversationId, message, authentication.getName());
    }

    @GetMapping
    public List<MessageResponse> getMessages(
            @PathVariable Long conversationId,
            Authentication authentication) {
        return messageService.getMessages(conversationId, authentication.getName());
    }
}
