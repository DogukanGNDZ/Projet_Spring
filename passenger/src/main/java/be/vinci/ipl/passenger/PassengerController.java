package be.vinci.ipl.passenger;

import be.vinci.ipl.passenger.exceptions.StatusNotInAcceptedValueException;
import be.vinci.ipl.passenger.models.Passenger.Etat;
import be.vinci.ipl.passenger.models.PassengerTrips;
import be.vinci.ipl.passenger.models.Passengers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PassengerController {

  private final PassengerService service;

  public PassengerController(PassengerService service) {
    this.service = service;
  }

  @PostMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<Void> addPassenger(@PathVariable long trip_id, @PathVariable long user_id) {
    boolean created = service.addPassenger(trip_id, user_id);
    if (!created) throw new ResponseStatusException(HttpStatus.CONFLICT);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<Void> deletePassenger(@PathVariable long trip_id, @PathVariable long user_id) {
    boolean deleted = service.deletePassenger(trip_id, user_id);
    if (!deleted) throw new ResponseStatusException(HttpStatus.CONFLICT);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<Void> updatePassenger(@PathVariable long trip_id, @PathVariable long user_id, @RequestParam String etat){
    if (!(etat == "ACCEPTED" || etat == "REFUSED")){throw new StatusNotInAcceptedValueException();}
    service.updateStatus(trip_id, user_id, etat);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/passengers/{trip_id}/{user_id}")
  public String getPassengerStatus(@PathVariable long trip_id, @PathVariable long user_id){
    return service.getPassengerStatus(trip_id, user_id);
  }

  @GetMapping("/passengers/{trip_id}")
  public Passengers getListOfPassengersOfATrip(@PathVariable long trip_id){
    return service.getListOfPassengersOfATrip(trip_id);
  }

  @DeleteMapping("/passengers/{trip_id}")
  public ResponseEntity<Void> deleteTrip(@PathVariable long trip_id){
    service.removeTrip(trip_id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/passengers/{user_id}")
  public PassengerTrips tripsOfAPassenger(@PathVariable long user_id){
    return service.tripsOfAUser(user_id);
  }

  @DeleteMapping("/passengers/{user_id}")
  public ResponseEntity<Void> removeUser(@PathVariable long user_id){
    service.removeUser(user_id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
