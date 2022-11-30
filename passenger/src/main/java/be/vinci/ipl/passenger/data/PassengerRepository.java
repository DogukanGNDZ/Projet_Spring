package be.vinci.ipl.passenger.data;

import be.vinci.ipl.passenger.models.Passenger;
import be.vinci.ipl.passenger.models.Passenger.Etat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, Long> {

  boolean existsByIdTripAndIdUser(long idTrip, long idUser);

  @Transactional
  void deleteByIdTripAndIdUser(long idTrip, long idUser);

  Passenger getPassengerByIdTripAndIdUser(long idTrip, long idUser);

  /*Optional<Passenger> findByIdTripAndIdUser(long idTrip, long idUser);*/

  boolean findByIdTripAndIdUser(long idTrip, long idUser);

  Iterable<Passenger> getPassengerByIdTripAndEtat(long idTrip, Etat etat);

  /*Iterable<Passenger> getAll();*/
}
