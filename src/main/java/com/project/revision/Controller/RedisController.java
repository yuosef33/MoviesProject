package com.project.revision.Controller;

import com.project.revision.Dto.ClientDto;
import com.project.revision.Service.Impl.ClientServiceImpl;
import com.project.revision.Service.Impl.EmailServiceImpl;
import com.project.revision.Service.Impl.RedisServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Redis")
public class RedisController {
    private final RedisServiceImpl redisService;
    private final EmailServiceImpl emailService;
    private final ClientServiceImpl clientService;
    public RedisController(RedisServiceImpl redisService, EmailServiceImpl emailService, ClientServiceImpl clientService) {
        this.redisService = redisService;
        this.emailService = emailService;
        this.clientService = clientService;
    }

    @PostMapping("/setRedis")
    public ResponseEntity<String> set() {
        redisService.save("Client:client23", "clientfuk", 600); // save for 10 minutes
        return ResponseEntity.ok("Saved");
    }

    @GetMapping("/getRedis")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok( redisService.get("Client:client23"));
    }

    @DeleteMapping("/deleteRedis")
    public ResponseEntity<String> delete(@RequestParam String key) {
        redisService.delete(key);
        return ResponseEntity.ok("Deleted");
    }
    @GetMapping("/send-email")
    public ResponseEntity<String> sendTestEmail() {
        emailService.sendSimpleMessage("jamalyuosef0@gmail.com", "Test Subject", "Hello from Spring!");
        return ResponseEntity.ok("Email sent !");
    }
    @GetMapping("/getChat")
    public ResponseEntity<List<Map<String,String>>> getChat() {
        return ResponseEntity.ok(redisService.getChatHistory(clientService.getCurrentClient().getUserEmail()));
    }

}
