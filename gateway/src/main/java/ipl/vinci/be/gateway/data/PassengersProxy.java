package ipl.vinci.be.gateway.data;

import ipl.vinci.be.gateway.models.Passenger.Etat;
import ipl.vinci.be.gateway.models.Passengers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "passenger")
public interface PassengersProxy {

  @PostMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<Void> addPassenger(@PathVariable long trip_id, @PathVariable long user_id);

  @DeleteMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<Void> deletePassenger(@PathVariable long trip_id, @PathVariable long user_id);

  @PutMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<Void> updatePassenger(@PathVariable long trip_id, @PathVariable long user_id, @RequestParam Etat etat);

  @GetMapping("/passengers/{trip_id}/{user_id}")
  public String getPassengerStatus(@PathVariable long trip_id, @PathVariable long user_id);

  @GetMapping("/passengers/{trip_id}")
  public Passengers getListOfPassengersOfATrip(@PathVariable long trip_id);

  @DeleteMapping("/passengers/{trip_id}")
  public ResponseEntity<Void> deleteTrip(@PathVariable long trip_id);

  @GetMapping("/passengers/{user_id}")
  public PassengerTrips tripsOfAPassenger(@PathVariable long user_id);

  @DeleteMapping("/passengers/{user_id}")
  public ResponseEntity<Void> removeUser(@PathVariable long user_id);
}
