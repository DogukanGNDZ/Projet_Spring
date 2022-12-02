package be.vinci.ipl.passenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoMoreSeatingException extends ResponseStatusException {
  public NoMoreSeatingException() {
    super(HttpStatus.BAD_REQUEST, "No more seating available");
  }
}
