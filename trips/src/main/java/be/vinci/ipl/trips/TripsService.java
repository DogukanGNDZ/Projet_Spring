package be.vinci.ipl.trips;

import be.vinci.ipl.trips.models.NewTrip;
import be.vinci.ipl.trips.models.Position;
import be.vinci.ipl.trips.models.Trip;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TripsService {

    private final TripsRepository repository;


    public TripsService(TripsRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new trip
     * @param trip The trip to create
     * @return The id of the product created
     */
    public Trip createOne(NewTrip trip){
        return repository.save(trip.toTrip());
    }

    /**
     * Reads a trip
     * @param id ID of the trip
     * @return the trip found, or null if none was found
     */
    public Trip readOne(int id){
        return repository.findById(id).orElse(null);
    }

    /**
     * Reads all the trip
     * @return The list of all trips
     */
    public Iterable<Trip> readAll(){
        return repository.findAll();
    }

    /**
     * Deletes a trip
     * @param id ID of the trip
     * @return True if the trip could be deleted, or false if the product couldn't be found
     */
    public boolean deleteOne(int id){
        if(!repository.existsById(id))
            return false;
        repository.deleteById(id);
        return true;
    }

    /**
     * Get trips where user is the driver with a future departure date
     * @param driver_id
     * @return an array of trips corresponding to driver_id
     */

    public Iterable<Trip> readAllTripsByDriverId(int driver_id){
        return repository.findAllByDriverId(driver_id);
    }

    /**
     * Delete all trips of a driver
     * @param driver_id
     */
    public void deleteAllTripsByDriverId(int driver_id){
        repository.deleteAllByDriverId(driver_id);
    }



    public Iterable<Trip> findOptionnalTrips(LocalDate departure, Double originLat, Double originLong, Double destinationLat, Double destinationLong) {
        Iterable<Trip> res = repository.findByAvailableSeatingIsGreaterThan(0);

        //Si la date de départ, l'origine et la destination est spécifié
        if(departure != null && originLat != null && originLong != null && destinationLat != null && destinationLong != null){
            res = StreamSupport.stream(res.spliterator(), false)
                    .filter(t -> t.getDeparture().equals(departure) &&
                            t.getOrigin().getLatitude() == originLat &&
                            t.getOrigin().getLongitude() == originLong &&
                            t.getDestination().getLatitude() == destinationLat &&
                            t.getDestination().getLongitude() ==destinationLong)
                    .collect(Collectors.toList());

        // Si la date de départ et l'origine est spécifié
        } else if (departure != null && originLat != null && originLong != null){
            res = StreamSupport.stream(res.spliterator(), false)
                    .filter(t -> t.getDeparture().equals(departure) &&
                            t.getOrigin().getLatitude() == originLat &&
                            t.getOrigin().getLongitude() == originLong)
                    .collect(Collectors.toList());

        // Si la date de départ et la destination est spécifié
        } else if(departure != null && destinationLat != null && destinationLong != null){
            res = StreamSupport.stream(res.spliterator(), false)
                    .filter(t -> t.getDeparture().equals(departure) &&
                            t.getDestination().getLatitude() == destinationLat &&
                            t.getDestination().getLongitude() ==destinationLong)
                    .collect(Collectors.toList());

        // Si l'origine et la destination est spécifié mais pas la date
        } else if (originLat != null && originLong != null && destinationLat != null && destinationLong != null) {
            res = StreamSupport.stream(res.spliterator(), false)
                    .filter(t -> t.getOrigin().getLatitude() == originLat &&
                            t.getOrigin().getLongitude() == originLong &&
                            t.getDestination().getLatitude() == destinationLat &&
                            t.getDestination().getLongitude() ==destinationLong)
                    .collect(Collectors.toList());

        // Si l'origine est spécifié mais pas la date ni la destination
        } else if (originLat != null && originLong != null) {
            res = StreamSupport.stream(res.spliterator(), false)
                    .filter(t -> t.getOrigin().getLatitude() == originLat &&
                            t.getOrigin().getLongitude() == originLong)
                    .collect(Collectors.toList());

        // Si la destination est specifié mais pas la date ni l'origine
        } else if (destinationLat != null && destinationLong != null) {
            res = StreamSupport.stream(res.spliterator(), false)
                    .filter(t -> t.getDestination().getLatitude() == destinationLat &&
                            t.getDestination().getLongitude() ==destinationLong)
                    .collect(Collectors.toList());

        // Si la date est specifié mais pas l'origine ni la destination
        } else if(departure != null){
            res = StreamSupport.stream(res.spliterator(), false)
                    .filter(t -> t.getDeparture().equals(departure))
                    .collect(Collectors.toList());
        }

        //Limite les résultats a 20 et ordonne la liste par date de création
        res = StreamSupport.stream(res.spliterator(), false)
                .limit(20)
                .sorted(Comparator.comparing(Trip::getCreationDate))
                .collect(Collectors.toList());

        return res;
    }
}
