package com.project.revision.Controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.project.revision.Config.JWT.TokenHandler;
import com.project.revision.Config.OAuth2.GoogleTokenVerifier;
import com.project.revision.Dao.ClientDao;
import com.project.revision.model.Auth;
import com.project.revision.model.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("OAuth2")
public class OAuth2Controller {
    private final GoogleTokenVerifier googleTokenVerifier;
    private final ClientDao clientDao;
    private final TokenHandler tokenHandler;

    public OAuth2Controller(GoogleTokenVerifier googleTokenVerifier, ClientDao clientDao, TokenHandler tokenHandler) {
        this.googleTokenVerifier = googleTokenVerifier;
        this.clientDao = clientDao;
        this.tokenHandler = tokenHandler;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> payload) {
        String idToken = payload.get("token");
        GoogleIdToken.Payload userInfo = googleTokenVerifier.verify(idToken);

        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        String email = userInfo.getEmail();
        String name = (String) userInfo.get("name");

        Client client = clientDao.findClientByUserEmail(email);
        if (client == null) {
            client = new Client();
            client.setUserEmail(email);
            client.setUserName(name);
            client.setUserPassword(UUID.randomUUID().toString());
            List<Auth> auths=new ArrayList<>();
            auths.add(new Auth(1L));
            client.setAuths(auths);
            clientDao.save(client);
        }

        client.getAuths().stream().forEach(auth -> auth.setUsers(null));
        String jwt = tokenHandler.creatToken(client);
        return ResponseEntity.ok(Map.of("token", jwt));
    }

}
