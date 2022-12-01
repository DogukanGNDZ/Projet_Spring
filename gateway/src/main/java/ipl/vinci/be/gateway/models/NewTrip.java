package ipl.vinci.be.gateway.models;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class NewTrip {

    private Position origin;
    private Position destination;
    private LocalDate departure;
    private long driver_id;
    private int available_seating;

}
