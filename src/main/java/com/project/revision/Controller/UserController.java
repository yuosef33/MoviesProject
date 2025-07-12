package com.project.revision.Controller;

import com.project.revision.Dto.*;
import com.project.revision.Mapper.ClientMapper;
import com.project.revision.Service.ClientService;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/User")
public class UserController {

    private final ClientService clientService;

    public UserController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> getGenre(@RequestBody LoginInfo loginInfo) throws SystemException {
        return ResponseEntity.ok(clientService.login(loginInfo));
    }

    @PostMapping("/signup")
    public ResponseEntity<ClientDto> creatAccount(@Valid @RequestBody ClientAccInfo clientAccInfo)throws SystemException {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientAccInfo));
    }

    @PostMapping("/signup2")
    public ResponseEntity<List<Map<String,String>>> creatRealAccount(@Valid @RequestBody ClientAccInfo clientAccInfo)throws SystemException {
        return ResponseEntity.ok(clientService.createUniqeClient(clientAccInfo));
    }
    @PostMapping("/verify")
    public ResponseEntity<ClientDto> creatRealAccount(@RequestParam String code,@RequestParam String id)throws SystemException {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.verifyAccount(code,id));
    }

    @PostMapping("/addImage/Upload")
    public ResponseEntity<String> uploadClientPhoto(@RequestParam MultipartFile image) throws IOException, SystemException  {
        return ResponseEntity.ok(clientService.uploadImage(image));
    }

    @GetMapping ("/data")
    public ResponseEntity<ClientDto> getUserdata()   {
        return ResponseEntity.ok(ClientMapper.toDto(clientService.getCurrentClient()));
    }

    @PutMapping("/update/data")
    public ResponseEntity<ClientDataUpdate> updateData(@RequestBody ClientDataUpdate clientDto )  {
    return ResponseEntity.ok(clientService.updateUserData(clientDto));
    }
    @PutMapping("/update/password")
    public ResponseEntity<Map<String,String>> updatePassword(@RequestBody PasswordUpdate passwordUpdate) throws SystemException {
        return ResponseEntity.ok(clientService.updateUserPassword(passwordUpdate));
    }


}
