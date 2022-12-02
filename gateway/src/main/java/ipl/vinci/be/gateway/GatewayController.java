package ipl.vinci.be.gateway;


import ipl.vinci.be.gateway.models.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
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
        System.out.println("la");
        service.createUser(user);
        System.out.println("pas la");
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

    @GetMapping("/users/{id}/driver")
    List<Trip> UserTripDriver(@PathVariable long id, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);
        if (user.getId()!=id) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return service.getTripsOfADriver(id);
    }




    @GetMapping("/users/{id}/passenger")
    PassengerTrips getTripsUserAPassenger(@PathVariable long id, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);
        if (user.getId()!=id) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return service.getTripsUserAPassenger(id);
    }


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

    @PostMapping("/trips")
     Trip createAtrip(@RequestBody NewTrip trip,  @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);
        if(trip.getDriver_id()!=user.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        if (userEmail==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return service.createTrip(trip);
    }

    @GetMapping("/trips")
    List<Trip> getListOfTripsWithOptional(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departure,
        @RequestParam(required = false) Double originLat,
        @RequestParam(required = false) Double originLong,
        @RequestParam(required = false) Double destinationLat,
        @RequestParam(required = false) Double destinationLong, @RequestHeader("Authorization") String token ){
        String userEmail = service.verify(token);
        if (userEmail==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return service.searchTrip(departure,originLat,originLong,destinationLat,destinationLong);
    }

    @GetMapping("/trips/{id}")
    Trip getInfoOnTrip(@PathVariable int id){
        return service.getInfoOnTrip(id);
    }

    @DeleteMapping("/trips/{id}")
    void deleteATrip(@PathVariable int id, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);
        Trip trip=service.getInfoOnTrip(id);
        if (user.getId()!=trip.getDriver_id()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        service.deleteATrip(id);
    }

    @GetMapping("/trips/{id}/passengers")
    Passengers getListPassengersTripByStatus(@PathVariable long id, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);
        Trip trip=service.getInfoOnTrip(id);
        if (user.getId()!=trip.getDriver_id()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return service.getPassengersTripByStatus(id);
    }

    @PostMapping("/trips/{trip_id}/passengers/{user_id}")
    void addPassengerToTrip(@PathVariable long trip_id, @PathVariable long user_id,  @RequestHeader("Authorization") String token) {
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);
        if (user.getId()!=user_id) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        service.addPassengerToATrip(trip_id, user_id);
    }

    @GetMapping("/trips/{trip_id}/passengers/{user_id}")
    String getPassengerStatus(@PathVariable long trip_id, @PathVariable long user_id,  @RequestHeader("Authorization") String token) {
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);
        Trip trip=service.getInfoOnTrip(trip_id);
        if (user.getId()!=user_id && user.getId()!=trip.getDriver_id()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return service.getPassengerStatus(trip_id, user_id);
    }

    @PutMapping("/trips/{trip_id}/passengers/{user_id}")
    void updatePassengerStatus(@PathVariable long trip_id, @PathVariable long user_id, @RequestParam String etat,  @RequestHeader("Authorization") String token) {
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);
        Trip trip=service.getInfoOnTrip(trip_id);
        if (user.getId()!=trip.getDriver_id()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        service.updatePassenger(trip_id, user_id, etat);
    }

    @DeleteMapping("/trips/{trip_id}/passengers/{user_id}")
    void deleteUserFromPassenger(@PathVariable long trip_id, @PathVariable long user_id,  @RequestHeader("Authorization") String token) {
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);
        if (user.getId()!=user_id) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        service.deletePassenger(trip_id, user_id);
    }
}
