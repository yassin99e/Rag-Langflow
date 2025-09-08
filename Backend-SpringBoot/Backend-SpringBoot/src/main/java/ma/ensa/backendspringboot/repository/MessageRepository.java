package ma.ensa.backendspringboot.repository;


import ma.ensa.backendspringboot.model.Conversation;
import ma.ensa.backendspringboot.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationOrderByTimestampAsc(Conversation conversation);
}