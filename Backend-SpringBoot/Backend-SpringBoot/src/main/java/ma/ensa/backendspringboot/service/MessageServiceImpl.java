package ma.ensa.backendspringboot.service;


import lombok.RequiredArgsConstructor;
import ma.ensa.backendspringboot.dto.MessageResponse;
import ma.ensa.backendspringboot.mapper.ConversationMapper;
import ma.ensa.backendspringboot.model.Conversation;
import ma.ensa.backendspringboot.model.Message;
import ma.ensa.backendspringboot.model.User;
import ma.ensa.backendspringboot.model.Sender;
import ma.ensa.backendspringboot.repository.ConversationRepository;
import ma.ensa.backendspringboot.repository.MessageRepository;
import ma.ensa.backendspringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final AiChatService aiChatService;

    public MessageResponse addUserMessage(Long conversationId, String content, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        if (!conversation.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        // Save User Message
        Message userMessage = new Message();
        userMessage.setConversation(conversation);
        userMessage.setSender(Sender.USER);
        userMessage.setContent(content);
        userMessage.setTimestamp(Instant.now());
        messageRepository.save(userMessage);

        // Call AI and save response
        String aiResponseText = aiChatService.getAiResponse(content);

        // Stub: Save AI Response placeholder (Langflow integration later)
        Message aiMessage = new Message();
        aiMessage.setConversation(conversation);
        aiMessage.setSender(Sender.AI);
        aiMessage.setContent(aiResponseText);
        aiMessage.setTimestamp(Instant.now());
        messageRepository.save(aiMessage);

        return ConversationMapper.toDto(aiMessage);

    }

    public List<MessageResponse> getMessages(Long conversationId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        if (!conversation.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        return messageRepository.findByConversationOrderByTimestampAsc(conversation)
                .stream()
                .map(ConversationMapper::toDto) // Map entity -> DTO
                .collect(Collectors.toList());
    }
}
