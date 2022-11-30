package be.vinci.ipl.passenger.data;


import be.vinci.ipl.passenger.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {
  @GetMapping("/users/{idUser}")
  User getUser(@PathVariable long idUser);
}
