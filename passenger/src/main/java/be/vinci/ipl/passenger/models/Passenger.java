package be.vinci.ipl.passenger.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Entity(name = "passenger")
public class Passenger {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "id_trip")
  private long idTrip;
  @Column(name = "id_user")
  private long idUser;
  private Etat etat;
  public enum Etat {
    PENDING,
    ACCEPTED,
    REFUSED
  }


  public NoIdPassenger removeId() {return new NoIdPassenger(idTrip,idUser,etat);}
}
