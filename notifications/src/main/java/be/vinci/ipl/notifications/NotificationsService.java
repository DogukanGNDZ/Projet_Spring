package be.vinci.ipl.notifications;

import org.springframework.stereotype.Service;

@Service
public class NotificationsService {

  private final NotificationsRepository repository;

  public NotificationsService(NotificationsRepository repository) {
    this.repository = repository;
  }

  /**
   * Creates a new Notification
   * @param notification The Notification to create
   * @return The new Notification
   */
  public Notification createOne(NoIdNotification notification){return repository.save(notification.toNotification());}

  /**
   * Get all notificattions of a user
   * @param idUser The id of the user
   * @return a arraylist of notifications
   */
  public Iterable<Notification> getUserNotifications(long idUser){return repository.findByUserId(idUser);}

  /**
   * delete all notifications of a user
   * @param idUser The id of the user
   */
  public void deleteAllNotificationsUser(long idUser){repository.deleteAllByUserId(idUser);}

  /**
   * delete all notifications from a trip
   * @param idTrip The id of the trip
   */
  public void deleteAllNotificationsTrip(long idTrip){repository.deleteAllByTripId(idTrip);}
}
