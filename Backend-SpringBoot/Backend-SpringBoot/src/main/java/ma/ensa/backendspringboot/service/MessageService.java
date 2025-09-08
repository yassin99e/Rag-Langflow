package ma.ensa.backendspringboot.service;

import ma.ensa.backendspringboot.dto.MessageResponse;
import ma.ensa.backendspringboot.model.Message;

import java.util.List;


public interface MessageService {

    MessageResponse addUserMessage(Long conversationId, String content, String userEmail);

    List<MessageResponse> getMessages(Long conversationId, String userEmail);

}
