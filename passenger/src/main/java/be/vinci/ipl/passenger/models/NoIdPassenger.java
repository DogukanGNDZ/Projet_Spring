package be.vinci.ipl.passenger.models;

import be.vinci.ipl.passenger.models.Passenger.Etat;
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
public class NoIdPassenger {
  private long idTrip;
  private long idUser;
  private Etat etat;
  public Passenger toPassenger() {
    return new Passenger(0L, idTrip, idUser, etat);
  }
  public Passenger toPassenger(long id) {
    return new Passenger(id, idTrip, idUser, etat);
  }
}
