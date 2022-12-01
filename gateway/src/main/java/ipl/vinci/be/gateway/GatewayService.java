package ipl.vinci.be.gateway;

import ipl.vinci.be.gateway.data.AuthenticationProxy;
import ipl.vinci.be.gateway.data.NotificationsProxy;
import ipl.vinci.be.gateway.data.PassengersProxy;
import ipl.vinci.be.gateway.data.TripsProxy;
import ipl.vinci.be.gateway.data.UsersProxy;
import ipl.vinci.be.gateway.models.*;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class GatewayService {
    private final AuthenticationProxy authenticationProxy;
    private final UsersProxy usersProxy;

    private final TripsProxy tripsProxy;
    private final NotificationsProxy notificationsProxy;
    private final PassengersProxy passengersProxy;

    public GatewayService(AuthenticationProxy authenticationProxy, UsersProxy usersProxy,
        TripsProxy tripsProxy, NotificationsProxy notificationsProxy,
        PassengersProxy passengersProxy) {
        this.authenticationProxy = authenticationProxy;
        this.usersProxy = usersProxy;
        this.tripsProxy = tripsProxy;
        this.notificationsProxy = notificationsProxy;
        this.passengersProxy = passengersProxy;
    }

    public String connect(Credentials credentials) {
        return authenticationProxy.connect(credentials);
    }

    public String verify(String token) {
        return authenticationProxy.verify(token);
    }
    public void createUser(UserWithCredentials user) {
        NewUser nu = user.toNewUser();
        usersProxy.createOne(nu);
        System.out.println("ici");
        authenticationProxy.createCredentials(user.getEmail(), user.toCredentials());
        System.out.println("fini");

    }

    public void updateUserPwd(Credentials user) {
        authenticationProxy.updateCredentials(user.getEmail(), user);
    }

    public void deleteUser(long id) {
        User user= usersProxy.readOneById(id);
        passengersProxy.removeUser(id);
        tripsProxy.deleteAllTripsByDriverId(id);
        notificationsProxy.deleteAllUserNotifications(id);
        authenticationProxy.deleteCredentials(user.getEmail());
        usersProxy.deleteOne(id);


    }

    public User readUser(String email) {
        return usersProxy.readOneByEmail(email);
    }
    public User readUserById(int id) {
        return usersProxy.readOneById(id);
    }
    public void updateUser(long id,User user) {
        usersProxy.updateOne(id, user);
    }
    public List<Trip> getTripsOfADriver(long id){
       return (List<Trip>) tripsProxy.readAllTripByDriverId(id);
    }

    public PassengerTrips getTripsUserAPassenger(long userId){
        return passengersProxy.tripsOfAPassenger(userId);
    }

    public Iterable<Notification> getNotification(long id){
      return  notificationsProxy.getUserNotifications(id);
    }
    public void deleteUserNotif(long id){
        notificationsProxy.deleteAllUserNotifications(id);
    }

    public Trip createTrip(NewTrip trip){
        return tripsProxy.createOne(trip);
    }

    public Trip getInfoOnTrip(long id){
        return tripsProxy.readOne(id);
    }
    public void deleteATrip(long id){
        notificationsProxy.deleteAllTripNotifications(id);
        tripsProxy.deleteOne(id);
    }

    public Passengers getPassengersTripByStatus(long tripId){
        return passengersProxy.getListOfPassengersOfATrip(tripId);
    }

    public void addPassengerToATrip(long tripId, long userId){
        Notification notification = new Notification(userId,tripId,LocalDate.now().toString(), "Passagé ajouté");
        notificationsProxy.createOne(notification);
        passengersProxy.addPassenger(tripId, userId);
    }

    public String getPassengerStatus(long tripId, long userId) {
        return passengersProxy.getPassengerStatus(tripId, userId);
    }

    public void updatePassenger(long tripId, long userId, String etat) {
        Notification notification = new Notification(userId,tripId,LocalDate.now().toString(), "Votre etat est maintenant: "+ etat);
        notificationsProxy.createOne(notification);
        passengersProxy.updatePassenger(tripId, userId, etat);
    }

    public void deletePassenger(long tripId, long userId) {
        Notification notification = new Notification(userId,tripId,LocalDate.now().toString(), "Passagé supprimé");
        notificationsProxy.createOne(notification);
        passengersProxy.deletePassenger(tripId, userId);
    }

    public List<Trip> searchTrip(LocalDate departure, Double originLat, Double originLong, Double destinationLat, Double destinationLong){
        return (List<Trip>) tripsProxy.readOptionalTrip(departure,originLat,originLong,destinationLat,destinationLong);
    }


}
