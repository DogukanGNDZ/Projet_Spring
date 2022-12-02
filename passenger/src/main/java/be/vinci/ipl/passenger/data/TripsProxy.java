package be.vinci.ipl.passenger.data;

import be.vinci.ipl.passenger.models.Trip;
import java.time.LocalDate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@FeignClient(name = "trips")
public interface TripsProxy {


  @GetMapping("trips/{id}")
  public Trip readOne(@PathVariable long id);

  @DeleteMapping("/trips/{id}")
  public void deleteOne(@PathVariable long id);

  @PutMapping("/trips/{id}")
  void updateOne(@RequestBody Trip trip);

  @GetMapping("/trips/driver/{user_id}")
  public Iterable<Trip> readAllTripByDriverId(@PathVariable long user_id);

  @DeleteMapping("/trips/driver/{user_id}")
  public void deleteAllTripsByDriverId(@PathVariable long user_id);

  @GetMapping("/trips")
  Iterable<Trip> readOptionalTrip(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departure,
      @RequestParam(required = false) Double originLat,
      @RequestParam(required = false) Double originLong,
      @RequestParam(required = false) Double destinationLat,
      @RequestParam(required = false) Double destinationLong);

}
