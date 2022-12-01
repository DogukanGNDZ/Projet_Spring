package be.vinci.ipl.passenger.data;


import be.vinci.ipl.passenger.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {

  @GetMapping("/users")
  User readOneByEmail (@RequestParam String email);

  @GetMapping("/users/{id}")
  User readOneById (@PathVariable long id);

  @PutMapping("/users/{id}")
  void updateOne(@PathVariable long id, @RequestBody User user);

  @DeleteMapping("/users/{id}")
  void deleteOne(@PathVariable long id);
}
