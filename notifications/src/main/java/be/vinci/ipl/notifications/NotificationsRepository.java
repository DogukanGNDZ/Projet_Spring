package be.vinci.ipl.notifications;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

public interface NotificationsRepository extends CrudRepository<Notification,Long>{

  Iterable<Notification>  findByUserId(long userId);
  @Transactional
  void deleteAllByUserId(long userId);
  @Transactional
  void deleteAllByTripId(long tripId);
}
