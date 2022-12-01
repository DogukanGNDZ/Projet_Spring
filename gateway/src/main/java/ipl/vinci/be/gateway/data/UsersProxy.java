package ipl.vinci.be.gateway.data;



import ipl.vinci.be.gateway.models.NewUser;
import ipl.vinci.be.gateway.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {

    @PostMapping("/users")
    User createOne(@RequestBody NewUser user);

    @GetMapping("/users")
    User readOneByEmail (@RequestParam String email);

    @GetMapping("/users/{id}")
    User readOneById (@PathVariable long id);

    @PutMapping("/users/{id}")
    void updateOne(@PathVariable long id, @RequestBody User user);

    @DeleteMapping("/users/{id}")
    void deleteOne(@PathVariable long id);
}
