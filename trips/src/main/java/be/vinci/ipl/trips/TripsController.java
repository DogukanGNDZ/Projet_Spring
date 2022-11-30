package be.vinci.ipl.trips;

import be.vinci.ipl.trips.models.NewTrip;
import be.vinci.ipl.trips.models.Trip;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
public class TripsController {

    private final TripsService service;

    public TripsController(TripsService service) {
        this.service = service;
    }

    @PostMapping("/trips")
    public Trip createOne(@RequestBody NewTrip trip){
        if(trip.getOrigin() == null || trip.getDestination() == null || trip.getDeparture() == null
                || trip.getAvailable_seating() <= 0 || trip.getDeparture().isBefore(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return service.createOne(trip);
    }

    @GetMapping("trips/{id}")
    public Trip readOne(@PathVariable int id){
        Trip trip = service.readOne(id);
        if(trip == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return trip;
    }

    @DeleteMapping("/trips/{id}")
    public void deleteOne(@PathVariable int id){
        if(!service.deleteOne(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/trips/driver/{user_id}")
    public Iterable<Trip> readAllTripByDriverId(@PathVariable int user_id){
        return service.readAllTripsByDriverId(user_id);
    }

    @DeleteMapping("/trips/driver/{user_id}")
    public void deleteAllTripsByDriverId(@PathVariable int user_id){
        service.deleteAllTripsByDriverId(user_id);
    }

    @GetMapping("/trips")
    public Iterable<Trip> readOptionalTrip(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departure,
                                           @RequestParam(required = false) Double originLat,
                                           @RequestParam(required = false) Double originLong,
                                           @RequestParam(required = false) Double destinationLat,
                                           @RequestParam(required = false) Double destinationLong) {
        return service.findOptionnalTrips(departure,originLat,originLong,destinationLat,destinationLong);
    }

}
