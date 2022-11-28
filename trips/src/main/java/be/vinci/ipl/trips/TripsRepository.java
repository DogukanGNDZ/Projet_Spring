package be.vinci.ipl.trips;

import be.vinci.ipl.trips.models.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

@Repository
public interface TripsRepository extends CrudRepository<Trip, Integer> {


    Iterable<Trip> findAllByDriverId(int driver_id);

    @Transactional
    void deleteAllByDriverId(int driver_id);

    Iterable<Trip> findByAvailableSeatingIsGreaterThan(int availableSeating);
}
