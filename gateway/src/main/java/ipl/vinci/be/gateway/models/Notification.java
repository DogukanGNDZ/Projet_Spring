package ipl.vinci.be.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Notification {

    private long userId;
    private long tripId;
    private String date;
    private String notificationText;

}
