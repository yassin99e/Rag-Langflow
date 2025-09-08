package ma.ensa.backendspringboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiChatService {

    @Value("${langflow.api.url}")
    private String apiUrl;

    @Value("${langflow.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAiResponse(String userMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);

        Map<String, Object> payload = Map.of(
                "output_type", "chat",
                "input_type", "chat",
                "input_value", userMessage
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);
            Map<String, Object> rawData = response.getBody();

            // Traverse nested JSON safely
            List<Map<String, Object>> outputs = (List<Map<String, Object>>) rawData.get("outputs");
            if (outputs.isEmpty()) throw new RuntimeException("No outputs from AI");

            Map<String, Object> firstOutput = outputs.get(0);
            List<Map<String, Object>> innerOutputs = (List<Map<String, Object>>) firstOutput.get("outputs");
            if (innerOutputs.isEmpty()) throw new RuntimeException("No inner outputs from AI");

            Map<String, Object> innerOutput = innerOutputs.get(0);
            Map<String, Object> results = (Map<String, Object>) innerOutput.get("results");
            if (results == null || !results.containsKey("message")) throw new RuntimeException("No message in AI response");

            Map<String, Object> messageMap = (Map<String, Object>) results.get("message");
            String text = (String) messageMap.get("text");

            return text;

        } catch (Exception e) {
            throw new RuntimeException("Error connecting to Langflow API: " + e.getMessage(), e);
        }
    }



}
