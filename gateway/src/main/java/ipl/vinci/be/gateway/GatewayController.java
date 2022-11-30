package ipl.vinci.be.gateway;


import ipl.vinci.be.gateway.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    @GetMapping("/users/{id}")
    User readUserById(@PathVariable int id, @RequestHeader("Authorization") String token){

        String userEmail = service.verify(token);
        User user = service.readUserById(id);
        if (!user.getEmail().equals(userEmail)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return user;
    }
    @PutMapping("/users/{id}")
    void updateUserById(@PathVariable int id,@RequestBody User user, @RequestHeader("Authorization") String token){

        String userEmail = service.verify(token);
        if (!user.getEmail().equals(userEmail)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        service.updateUser(id,user);
    }
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable int id, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUserById(id);
        if (!user.getEmail().equals(userEmail)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        service.deleteUser(id);
    }
    /*
    ligne 157 du YAML
    @GetMapping("/users/{id}/driver")
    List<Trip> UserTripDriver(@PathVariable int id, @RequestHeader("Authorization") String token){
        //besoin de trip je suis agenoux
    }

    */

/*

ligne 187 du YML
    @GetMapping("/users/{id}/passenger")
    Passenger_trips UserTripDriver(@PathVariable int id, @RequestHeader("Authorization") String token){
        //besoin de passenger je suis agenoux
    }
*/

    @GetMapping("/users/{id}/notifications")
    List<Notification> getUserNotification(@PathVariable int id, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUserById(id);
        if (!user.getEmail().equals(userEmail)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return (List<Notification>) service.getNotification(id);
    }
    @DeleteMapping("/users/{id}/notifications")
    void deleteUserNotif(@PathVariable int id, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUserById(id);
        if (!user.getEmail().equals(userEmail)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        service.deleteUserNotif(id);
    }



}
