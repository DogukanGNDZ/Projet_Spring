package be.vinci.ipl.passenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StatusNotInAcceptedValueException extends ResponseStatusException {
  public StatusNotInAcceptedValueException() {
    super(HttpStatus.BAD_REQUEST, "Status should be accepted or refused");
  }
}
