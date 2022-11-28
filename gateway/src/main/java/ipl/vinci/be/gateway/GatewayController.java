package ipl.vinci.be.gateway;


import ipl.vinci.be.gateway.models.Credentials;
import ipl.vinci.be.gateway.models.User;
import ipl.vinci.be.gateway.models.UserWithCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = {"https://www.ChattyCar.com", "http://localhost"})
@RestController
public class GatewayController {


    private final GatewayService service;

    public GatewayController(GatewayService service) {
        this.service = service;
    }

    @PostMapping("/auth")
    String connect(@RequestBody Credentials credentials) {
        return service.connect(credentials);
    }

    @PostMapping("/users")
    ResponseEntity<Void> createUser(@RequestBody UserWithCredentials user) {
        service.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users")
    User readUser(@RequestParam String email) {
        return service.readUser(email);
    }

    @PutMapping("/users")
    void updateUser(@RequestBody Credentials user, @RequestHeader("Authorization") String token) {

        String userEmail = service.verify(token);
        if (!user.getEmail().equals(userEmail)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        service.updateUserPwd(user);

    }
}
