package be.vinci.ipl.notifications;
import org.springframework.data.repository.CrudRepository;

public interface NotificationsRepository extends CrudRepository<Notification,Long>{

  Iterable<Notification>  findByUserId(long userId);
  void removeAllByUserId(long userId);
  boolean removeAllByTripId(long tripId);
}
