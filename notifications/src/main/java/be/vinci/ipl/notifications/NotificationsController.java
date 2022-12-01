package be.vinci.ipl.notifications;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class NotificationsController {
  private final NotificationsService service;

  public NotificationsController(NotificationsService service) {
    this.service = service;
  }

  @PostMapping("/notifications")
  public Notification createOne(@RequestBody NoIdNotification notification){
    if(notification.getNotificationText()==null||notification.getDate()==null){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return service.createOne(notification);
  }

  @GetMapping("/notifications/user/{id}")
  public Iterable<Notification> getUserNotifications(@PathVariable long id){
    Iterable<Notification> listNotifications = service.getUserNotifications(id);
    if (listNotifications==null){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return listNotifications;
  }

  @DeleteMapping("/notifications/user/{id}")
  public void deleteAllUserNotifications(@PathVariable long id){
    if(service.getUserNotifications(id)==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    service.deleteAllNotificationsUser(id);
  }

  @DeleteMapping("/notifications/trip/{id}")
  public void deleteAllTripNotifications(@PathVariable long id){
    service.deleteAllNotificationsTrip(id);
  }
}
