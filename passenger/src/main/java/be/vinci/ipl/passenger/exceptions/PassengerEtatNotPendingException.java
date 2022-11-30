package be.vinci.ipl.passenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PassengerEtatNotPendingException extends ResponseStatusException {
  public PassengerEtatNotPendingException() {
    super(HttpStatus.BAD_REQUEST, "This user is not in pending status");
  }
}
