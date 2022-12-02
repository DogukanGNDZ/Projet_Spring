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

//j'ai change les int en long ne pas oublie de change dans le service
    @PostMapping("/trips")
    public Trip createOne(@RequestBody NewTrip trip);

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
