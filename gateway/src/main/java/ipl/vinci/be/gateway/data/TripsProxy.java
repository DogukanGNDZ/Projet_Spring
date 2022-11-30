package ipl.vinci.be.gateway.data;

import ipl.vinci.be.gateway.models.NewTrip;
import ipl.vinci.be.gateway.models.Trip;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Repository
@FeignClient(name = "trips")
public interface TripsProxy {


    @PostMapping("/trips")
    public Trip createOne(@RequestBody NewTrip trip);

    @GetMapping("trips/{id}")
    public Trip readOne(@PathVariable int id);

    @DeleteMapping("/trips/{id}")
    public void deleteOne(@PathVariable int id);

    @GetMapping("/trips/driver/{user_id}")
    public Iterable<Trip> readAllTripByDriverId(@PathVariable int user_id);

    @DeleteMapping("/trips/driver/{user_id}")
    public void deleteAllTripsByDriverId(@PathVariable int user_id);
    @GetMapping("/trips")
    public Iterable<Trip> readOptionnalTrip(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departure, @RequestParam(required = false) double originLat, @RequestParam(required = false) double originLong, @RequestParam(required = false)double destinationLat, @RequestParam(required = false)double destinationLong);

}
