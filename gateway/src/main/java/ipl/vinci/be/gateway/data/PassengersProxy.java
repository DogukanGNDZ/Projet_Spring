package ipl.vinci.be.gateway.data;

import ipl.vinci.be.gateway.models.Passenger.Etat;
import ipl.vinci.be.gateway.models.PassengerTrips;
import ipl.vinci.be.gateway.models.Passengers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "passengers")
public interface PassengersProxy {

  @PostMapping("/passengers/{trip_id}/{user_id}")
  ResponseEntity<Void> addPassenger(@PathVariable long trip_id, @PathVariable long user_id);

  @DeleteMapping("/passengers/{trip_id}/{user_id}")
  ResponseEntity<Void> deletePassenger(@PathVariable long trip_id, @PathVariable long user_id);

  @PutMapping("/passengers/{trip_id}/{user_id}")
  ResponseEntity<Void> updatePassenger(@PathVariable long trip_id, @PathVariable long user_id, @RequestParam String etat);

  @GetMapping("/passengers/{trip_id}/{user_id}")
  String getPassengerStatus(@PathVariable long trip_id, @PathVariable long user_id);

  @GetMapping("/passengers/trips/{trip_id}")
  Passengers getListOfPassengersOfATrip(@PathVariable long trip_id);

  @DeleteMapping("/passengers/trips/{trip_id}")
  ResponseEntity<Void> deleteTrip(@PathVariable long trip_id);

  @GetMapping("/passengers/users/{user_id}")
  PassengerTrips tripsOfAPassenger(@PathVariable long user_id);

  @DeleteMapping("/passengers/users/{user_id}")
  ResponseEntity<Void> removeUser(@PathVariable long user_id);
}
