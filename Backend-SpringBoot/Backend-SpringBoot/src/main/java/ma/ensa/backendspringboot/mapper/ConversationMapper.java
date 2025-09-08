package ma.ensa.backendspringboot.mapper;

import ma.ensa.backendspringboot.dto.ConversationResponse;
import ma.ensa.backendspringboot.dto.MessageResponse;
import ma.ensa.backendspringboot.model.Conversation;
import ma.ensa.backendspringboot.model.Message;

import java.util.stream.Collectors;

public class ConversationMapper {

    public static ConversationResponse toDto(Conversation conversation, boolean includeMessages) {
        return ConversationResponse.builder()
                .id(conversation.getId())
                .title(conversation.getTitle())
                .createdAt(conversation.getCreatedAt())
                .messages(includeMessages
                        ? conversation.getMessages().stream()
                        .map(ConversationMapper::toDto)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    public static MessageResponse toDto(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .sender(message.getSender().name())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }
}
