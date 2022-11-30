package be.vinci.ipl.passenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TripOrUserNotFound404Exception extends ResponseStatusException {
  public TripOrUserNotFound404Exception() {
    super(HttpStatus.NOT_FOUND, "Trip or user not found");
  }
}
