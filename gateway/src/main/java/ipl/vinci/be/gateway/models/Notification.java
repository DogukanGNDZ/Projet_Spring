package ipl.vinci.be.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Notification {

    private int user_id;
    private int trip_id;
    private String date;
    private String notification_text;

}
