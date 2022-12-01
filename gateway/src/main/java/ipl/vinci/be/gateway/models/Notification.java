package ipl.vinci.be.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Notification {

    private long user_id;
    private long trip_id;
    private String date;
    private String notification_text;

}
