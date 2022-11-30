package be.vinci.ipl.passenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TripNotFoundException extends ResponseStatusException {
  public TripNotFoundException() {
    super(HttpStatus.NOT_FOUND, "No trip found with this ID");
  }

}
