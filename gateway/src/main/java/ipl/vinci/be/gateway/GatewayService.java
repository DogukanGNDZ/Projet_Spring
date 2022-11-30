package ipl.vinci.be.gateway;

import ipl.vinci.be.gateway.data.AuthenticationProxy;
import ipl.vinci.be.gateway.data.NotificationsProxy;
import ipl.vinci.be.gateway.data.TripsProxy;
import ipl.vinci.be.gateway.data.UsersProxy;
import ipl.vinci.be.gateway.models.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatewayService {
    private final AuthenticationProxy authenticationProxy;
    private final UsersProxy usersProxy;

    private final TripsProxy tripsProxy;
    private final NotificationsProxy notificationsProxy;

    public GatewayService(AuthenticationProxy authenticationProxy, UsersProxy usersProxy, TripsProxy tripsProxy, NotificationsProxy notificationsProxy) {
        this.authenticationProxy = authenticationProxy;
        this.usersProxy = usersProxy;
        this.tripsProxy = tripsProxy;
        this.notificationsProxy = notificationsProxy;
    }

    public String connect(Credentials credentials) {
        return authenticationProxy.connect(credentials);
    }

    public String verify(String token) {
        return authenticationProxy.verify(token);
    }
    public void createUser(UserWithCredentials user) {
        usersProxy.createOne(user.toNewUser());
        authenticationProxy.createCredentials(user.getEmail(), user.toCredentials());
    }

    public void updateUserPwd(Credentials user) {
        authenticationProxy.updateCredentials(user.getEmail(), user);
    }

    public void deleteUser(long id) {
        User user= usersProxy.readOneById(id);
        authenticationProxy.deleteCredentials(user.getEmail());
        notificationsProxy.deleteAllUserNotifications(id);
        usersProxy.deleteOne(id);

        //des chose a faire jerome fais attention
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

    public Iterable<Notification> getNotification(long id){
      return  notificationsProxy.getUserNotifications(id);
    }
    public void deleteUserNotif(long id){
        notificationsProxy.deleteAllUserNotifications(id);
    }

    public Trip createTrip(NewTrip trip){
        return tripsProxy.createOne(trip);
    }

}
