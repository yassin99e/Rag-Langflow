package ma.ensa.backendspringboot.service;

import ma.ensa.backendspringboot.dto.ConversationResponse;
import ma.ensa.backendspringboot.model.Conversation;

import java.util.List;

public interface ConversationService {

    ConversationResponse createConversation(String userEmail);

    List<ConversationResponse> getUserConversations(String userEmail);

    ConversationResponse updateConversationTitle(Long conversationId, String title, String userEmail);

}
