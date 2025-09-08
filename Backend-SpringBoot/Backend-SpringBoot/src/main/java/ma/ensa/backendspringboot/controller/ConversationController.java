package ma.ensa.backendspringboot.controller;

import lombok.RequiredArgsConstructor;
import ma.ensa.backendspringboot.dto.ConversationResponse;
import ma.ensa.backendspringboot.model.Conversation;
import ma.ensa.backendspringboot.service.ConversationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping
    public ConversationResponse createConversation(Authentication authentication) {
        return conversationService.createConversation(authentication.getName());
    }

    @GetMapping
    public List<ConversationResponse> getUserConversations(Authentication authentication) {
        return conversationService.getUserConversations(authentication.getName());
    }

    @PutMapping("/{id}/title")
    public ConversationResponse updateConversationTitle(
            @PathVariable Long id,
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        String title = request.get("title");
        return conversationService.updateConversationTitle(id, title, authentication.getName());
    }
}
