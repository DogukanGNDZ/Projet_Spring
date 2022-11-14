package be.vinci.ipl.notifications;
import org.springframework.data.repository.CrudRepository;

public interface NotificationsRepository extends CrudRepository<Notification,Integer>{

  Iterable<Notification>  findByUser_id(int user_id);
  Notification findByUser_idAndAndTrip_id(int user_id, int trip_id);

}
