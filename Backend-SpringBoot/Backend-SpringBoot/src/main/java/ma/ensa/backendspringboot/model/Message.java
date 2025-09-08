package ma.ensa.backendspringboot.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Sender sender;  // USER or AI

    @Column(columnDefinition = "TEXT")
    private String content;

    private Instant timestamp = Instant.now();

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
}
