package be.vinci.ipl.passenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotPassengerException extends ResponseStatusException {

  public UserNotPassengerException() {
    super(HttpStatus.BAD_REQUEST, "This user is not a passenger of the trip");
  }

}
