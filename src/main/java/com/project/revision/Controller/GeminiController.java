package com.project.revision.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.revision.Service.Impl.GeminiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/ask")
    public ResponseEntity<?> askAI(@RequestBody Map<String, String> body) throws JsonProcessingException {
        String prompt = body.get("question");
        String answer = geminiService.askGemini(prompt);
        return ResponseEntity.ok(Map.of("response", answer));
    }
    @DeleteMapping("/endChat")
    public ResponseEntity<String> DeleteChat(){
        geminiService.deleteCurrentUserChat();
        return ResponseEntity.status(HttpStatus.FOUND).body("Deleted");
    }
}
