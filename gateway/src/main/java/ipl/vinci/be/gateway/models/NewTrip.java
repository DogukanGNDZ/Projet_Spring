package ipl.vinci.be.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class NewTrip {

    private Position origin;
    private Position destination;
    private String departure_date;
    private long driver_id;
    private int available_seating;

}
