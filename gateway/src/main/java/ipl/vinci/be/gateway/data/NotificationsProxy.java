package ipl.vinci.be.gateway.data;


import ipl.vinci.be.gateway.models.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "notifications")
public interface NotificationsProxy {

    @PostMapping("/notifications")
    public Notification createOne(@RequestBody Notification notification);


    @GetMapping("/notifications/user/{id}")
    public Iterable<Notification> getUserNotifications(@PathVariable long id);


    @DeleteMapping("/notifications/user/{id}")
    public void deleteAllUserNotifications(@PathVariable long id);

    @DeleteMapping("/notifications/trip/{id}")
    public void deleteAllTripNotifications(@PathVariable long id);

}
