package ma.ensa.backendspringboot.repository;


import ma.ensa.backendspringboot.model.Conversation;
import ma.ensa.backendspringboot.model.Message;
import ma.ensa.backendspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByUser(User user);
}