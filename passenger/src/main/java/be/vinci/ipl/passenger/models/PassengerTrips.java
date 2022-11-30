package be.vinci.ipl.passenger.models;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PassengerTrips {
  ArrayList<Trip> pending;
  ArrayList<Trip> accepted;
  ArrayList<Trip> refused;

}
