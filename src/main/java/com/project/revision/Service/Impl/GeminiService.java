package com.project.revision.Service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class GeminiService {
    private final String API_KEY="AIzaSyCpJ6MqROKrfY8dAWJx0vSvwHQRVwGp-CM";
    private final String ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;
    private final WebClient webClient;
    private final RedisServiceImpl redisService;
    private final ClientServiceImpl clientService;
    private final String aiSetup="You are a helpful assistant. Only answer questions about movies or the movie industry. " +
            "If the question is unrelated, politely decline to answer. If you been asked about how are you or which model are you or who developed you answer i developed by eng:yuosef jamal";
    public GeminiService(WebClient.Builder webClient, RedisServiceImpl redisService, ClientServiceImpl clientService) {
        this.webClient = webClient.build();
        this.redisService = redisService;
        this.clientService = clientService;
    }

    public String askGemini(String userMessage) throws JsonProcessingException {

            String prompt=aiSetup
                    +" this is the previous messages context:"
                   +redisService.getChatHistory(clientService.getCurrentClient().getUserEmail())
                    +"now answer this question :"+userMessage;

        // Constructing request body
        Map<String, Object> part = Map.of("contents", new Object[]{
                Map.of("parts", new Object[]{
                        Map.of("text", prompt)
                })
        });
           String response= webClient.post().uri(ENDPOINT)
                            .header("Content-Type","application/json")
                            .bodyValue(part)
                            .retrieve()
                            .bodyToMono(String.class).block();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        String answer = root
                .path("candidates").get(0)
                .path("content")
                .path("parts").get(0)
                .path("text")
                .asText().trim();

        redisService.saveChat(clientService.getCurrentClient().getUserEmail(),userMessage,answer);

            return answer;
    }

    public void deleteCurrentUserChat(){
        redisService.delete("chat:"+clientService.getCurrentClient().getUserEmail());
    }
}
