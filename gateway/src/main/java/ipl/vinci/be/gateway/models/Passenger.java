package ipl.vinci.be.gateway.models;

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

public class Passenger {
  private long id;
  private long idTrip;
  private long idUser;
  private Etat etat;
  public enum Etat {
    PENDING,
    ACCEPTED,
    REFUSED
  }
}
