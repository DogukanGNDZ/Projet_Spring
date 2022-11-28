package be.vinci.ipl.notifications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoIdNotification {
  private long userId;
  private long tripId;
  private String date;
  private String notificationText;
  public Notification toNotification(){return new Notification(0L,userId,tripId,date,notificationText);}
}
