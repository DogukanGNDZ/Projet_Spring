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
@AllArgsConstructor
@NoArgsConstructor
public class Passengers {
  ArrayList<User> pending;
  ArrayList<User> accepted;
  ArrayList<User> refused;
}
