package ma.ensa.backendspringboot.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class MessageResponse {
    private Long id;
    private String sender;
    private String content;
    private Instant timestamp;
}
