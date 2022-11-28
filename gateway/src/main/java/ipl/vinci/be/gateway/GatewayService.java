package ipl.vinci.be.gateway;

import ipl.vinci.be.gateway.data.AuthenticationProxy;
import ipl.vinci.be.gateway.data.NotificationsProxy;
import ipl.vinci.be.gateway.data.UsersProxy;
import ipl.vinci.be.gateway.models.Credentials;
import ipl.vinci.be.gateway.models.User;
import ipl.vinci.be.gateway.models.UserWithCredentials;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {
    private final AuthenticationProxy authenticationProxy;
    private final UsersProxy usersProxy;

    private final NotificationsProxy notificationsProxy;

    public GatewayService(AuthenticationProxy authenticationProxy, UsersProxy usersProxy, NotificationsProxy notificationsProxy) {
        this.authenticationProxy = authenticationProxy;
        this.usersProxy = usersProxy;
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
}
