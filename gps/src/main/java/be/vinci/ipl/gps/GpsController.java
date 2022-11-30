package be.vinci.ipl.gps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GpsController {

  @GetMapping("/gps")
  public double getDistance(@RequestParam double originLat, @RequestParam double originLon,
      @RequestParam double destinationLat, @RequestParam double destinationLon) {

    double distanceLat = destinationLat - originLat;
    double distanceLon = destinationLon - originLon;
    double dLatAuCarre = distanceLat * distanceLat;
    double dLonAuCarre = distanceLon * distanceLon;
    double distance = Math.sqrt(dLonAuCarre+dLatAuCarre);
    return distance;
  }
}
