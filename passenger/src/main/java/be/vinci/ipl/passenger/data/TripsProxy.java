package be.vinci.ipl.passenger.data;

import be.vinci.ipl.passenger.models.Trip;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@FeignClient(name = "trips")
public interface TripsProxy {
  @DeleteMapping("/trips/{id}")
  void deleteTrip(@PathVariable long id);

  @GetMapping("/passengers/{user_id}")
  Iterable<Trip> getAll();

  @GetMapping("/passengers/{user_id}")
  Trip getTrip(@RequestParam long idTrip);

}
