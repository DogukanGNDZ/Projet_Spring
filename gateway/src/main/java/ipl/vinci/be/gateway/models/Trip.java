package ipl.vinci.be.gateway.models;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Trip {

    private long id;
    private Position origin;
    private Position destination;
    private String departure;
    private long driver_id;
    private int available_seating;


}
