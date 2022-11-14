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


}
