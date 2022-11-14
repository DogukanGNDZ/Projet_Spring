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
  private int user_id;
  private int trip_id;
  private String date;
  private String notification_text;
  public Notification toNotification(){return new Notification(0,user_id,trip_id,date,notification_text);}
}
