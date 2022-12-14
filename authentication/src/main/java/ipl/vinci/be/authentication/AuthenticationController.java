package ipl.vinci.be.authentication;

import ipl.vinci.be.authentication.models.Credentials;
import ipl.vinci.be.authentication.models.InsecureCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    @PostMapping("/authentication/connect")
    public String connect(@RequestBody Credentials credentials) {
        InsecureCredentials credentials1 = new InsecureCredentials();
        credentials1.setEmail(credentials.getEmail());
        credentials1.setPassword(credentials.getPassword());
        String token =  service.connect(credentials1);
        if (token == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return token;
    }


    @PostMapping("/authentication/verify")
    public String verify(@RequestBody String token) {
        String pseudo = service.verify(token);
        if (pseudo == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return pseudo;
    }


    @PostMapping("/authentication/{email}")
    public ResponseEntity<Void> createOne(@PathVariable String email, @RequestBody InsecureCredentials credentials) {
        if (credentials.getEmail() == null || credentials.getPassword() == null ||
                !credentials.getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        System.out.println("ici");
        boolean created = service.createOne(credentials);
        if (!created) throw new ResponseStatusException(HttpStatus.CONFLICT);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/authentication/{email}")
    public void updateOne(@PathVariable String email, @RequestBody InsecureCredentials credentials) {
        if (credentials.getEmail() == null || credentials.getPassword() == null ||
                !credentials.getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean found = service.updateOne(credentials);
        if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/authentication/{email}")
    public void deleteCredentials(@PathVariable String email) {
        boolean found = service.deleteOne(email);
        if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
