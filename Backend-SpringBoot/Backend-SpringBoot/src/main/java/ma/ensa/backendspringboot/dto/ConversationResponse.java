package ma.ensa.backendspringboot.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ConversationResponse {
    private Long id;
    private String title;
    private Instant createdAt;
    private List<MessageResponse> messages; // Only if you want messages included
}
