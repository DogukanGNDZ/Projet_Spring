package be.vinci.ipl.passenger;

import be.vinci.ipl.passenger.data.PassengerRepository;
import be.vinci.ipl.passenger.data.TripsProxy;
import be.vinci.ipl.passenger.data.UsersProxy;
import be.vinci.ipl.passenger.exceptions.PassengerEtatNotPendingException;
import be.vinci.ipl.passenger.exceptions.TripNotFoundException;
import be.vinci.ipl.passenger.exceptions.TripOrUserNotFound404Exception;
import be.vinci.ipl.passenger.exceptions.UserNotFoundException;
import be.vinci.ipl.passenger.exceptions.UserNotPassengerException;
import be.vinci.ipl.passenger.models.NoIdPassenger;
import be.vinci.ipl.passenger.models.Passenger;
import be.vinci.ipl.passenger.models.Passenger.Etat;
import be.vinci.ipl.passenger.models.PassengerTrips;
import be.vinci.ipl.passenger.models.Passengers;
import be.vinci.ipl.passenger.models.Trip;
import be.vinci.ipl.passenger.models.User;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
  private final PassengerRepository repo;
  private final UsersProxy user;
  private final TripsProxy trips;

  public PassengerService(PassengerRepository repo, UsersProxy user,
      TripsProxy trips) {

    this.repo = repo;
    this.user = user;
    this.trips = trips;
  }

  public boolean addPassenger(long idTrip, long idUser) {
    if (repo.existsByIdTripAndIdUser(idTrip, idUser)) return false;
    NoIdPassenger noIdPassenger = new NoIdPassenger(idTrip, idUser, Etat.PENDING);
    repo.save(noIdPassenger.toPassenger());
    return true;
  }

  public boolean deletePassenger(long idTrip, long idUser){
    if (!repo.existsByIdTripAndIdUser(idTrip, idUser)){
      return false;
    }
    repo.deleteByIdTripAndIdUser(idTrip, idUser);
    return true;
  }

  public void updateStatus(long idTrip, long idUser, String etat) throws TripOrUserNotFound404Exception {
    if (!repo.existsByIdTripAndIdUser(idTrip, idUser)){throw new UserNotPassengerException();}
    if (repo.getPassengerByIdTripAndIdUser(idTrip, idUser).getEtat() != Etat.PENDING){throw new PassengerEtatNotPendingException();}
    Passenger oldPassenger = repo.getPassengerByIdTripAndIdUser(idTrip, idUser);
    if (oldPassenger == null){
      throw new TripOrUserNotFound404Exception();
    }
    NoIdPassenger noIdPassenger;
    if (etat == "ACCEPTED") {
       noIdPassenger = new NoIdPassenger(idTrip, idUser, Etat.ACCEPTED);
    } else{
       noIdPassenger = new NoIdPassenger(idTrip, idUser, Etat.REFUSED);
  }

    repo.save(noIdPassenger.toPassenger(oldPassenger.getId()));
  }

  public String getPassengerStatus(long idTrip, long idUser) throws TripOrUserNotFound404Exception {
    Passenger passenger = repo.getPassengerByIdTripAndIdUser(idTrip, idUser);
    if (passenger==null) {
      throw new TripOrUserNotFound404Exception();
    }
    return passenger.getEtat().toString();
  }

  public Passengers getListOfPassengersOfATrip(long idTrip) {
    Iterable<Passenger> passengersPending = repo.getPassengerByIdTripAndEtat(idTrip, Etat.PENDING);
    Iterable<Passenger> passengersAccepted = repo.getPassengerByIdTripAndEtat(idTrip, Etat.ACCEPTED);
    Iterable<Passenger> passengersRefused = repo.getPassengerByIdTripAndEtat(idTrip, Etat.REFUSED);
    if (passengersAccepted == null && passengersRefused == null && passengersPending == null){
      throw new TripNotFoundException();
    }
    ArrayList<User> usersPending = new ArrayList<>();
    ArrayList<User> usersAccepted = new ArrayList<>();
    ArrayList<User> usersRefused = new ArrayList<>();
    for (Passenger p: passengersPending) {
      usersPending.add(user.readOneById(p.getId())) ;
    }
    for (Passenger p: passengersRefused) {
      usersRefused.add(user.readOneById(p.getId())) ;
    }
    for (Passenger p: passengersAccepted) {
      usersAccepted.add(user.readOneById(p.getId())) ;
    }

    return new Passengers(usersPending, usersAccepted, usersRefused);

  }

  public void removeTrip(long idTrip) {
    Passengers passengers = getListOfPassengersOfATrip(idTrip);
    ArrayList<User> users = new ArrayList<>();
    users.addAll(passengers.getAccepted());
    users.addAll(passengers.getPending());
    users.addAll(passengers.getRefused());
    for (User u:users) {
      deletePassenger(idTrip, u.getId());
    }
    trips.deleteTrip(idTrip);

  }

  public PassengerTrips tripsOfAUser(long idUser){
    if (user.readOneById(idUser)== null){
      throw new UserNotFoundException();
    }
    Iterable<Trip> tripIterable = trips.readOptionalTrip();
    ArrayList<Trip> tripsPending =new ArrayList<>();
    ArrayList<Trip> tripsAccepted =new ArrayList<>();
    ArrayList<Trip> tripsRefused =new ArrayList<>();
    for (Trip t: tripIterable) {
      if (repo.existsByIdTripAndIdUser(t.getId(),idUser)){
        Passenger p = repo.getPassengerByIdTripAndIdUser(t.getId(), idUser);
        if (p.getEtat() == Etat.ACCEPTED){
          tripsAccepted.add(trips.getTrip(t.getId()));
        }
        if (p.getEtat() == Etat.REFUSED){
          tripsRefused.add(trips.getTrip(t.getId()));
        }
        if (p.getEtat() == Etat.PENDING){
          tripsPending.add(trips.getTrip(t.getId()));
        }
      }
    }
    return new PassengerTrips(tripsPending, tripsAccepted, tripsRefused);
  }

  public void removeUser(long userId){
    if (user.readOneById(userId)== null){
      throw new UserNotFoundException();
    }
    Iterable<Trip> tripIterable = trips.readOptionalTrip();
    for (Trip t: tripIterable) {
      if (repo.findByIdTripAndIdUser(t.getId(), userId)){
        deletePassenger(t.getId(), userId);
      }
    }
  }


}
