package ma.ensa.backendspringboot.service;


import lombok.RequiredArgsConstructor;
import ma.ensa.backendspringboot.dto.ConversationResponse;
import ma.ensa.backendspringboot.mapper.ConversationMapper;
import ma.ensa.backendspringboot.model.Conversation;
import ma.ensa.backendspringboot.model.User;
import ma.ensa.backendspringboot.repository.ConversationRepository;
import ma.ensa.backendspringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public ConversationResponse createConversation(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Conversation conversation = new Conversation();
        conversation.setTitle("Unnamed Conversation");
        conversation.setUser(user);

        return ConversationMapper.toDto(conversationRepository.save(conversation),false);
    }

    public List<ConversationResponse> getUserConversations(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Conversation> conversations = conversationRepository.findByUser(user);

        // Map entities -> DTOs (without messages)
        return conversations.stream()
                .map(conv -> ConversationMapper.toDto(conv, false))
                .collect(Collectors.toList());
    }

    public ConversationResponse updateConversationTitle(Long conversationId, String title, String userEmail) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        if (!conversation.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized");
        }

        conversation.setTitle(title);
        return ConversationMapper.toDto(conversationRepository.save(conversation),false);
    }
}
