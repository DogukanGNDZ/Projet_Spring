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
    public User createOne(@RequestBody NewUser user);

    @GetMapping("/users")
    public User readOneByEmail (@RequestParam String email);

    @GetMapping("/users/{id}")
    public User readOneById (@PathVariable long id);

    @PutMapping("/users/{id}")
    public void updateOne(@PathVariable long id, @RequestBody User user);

    @DeleteMapping("/users/{id}")
    public void deleteOne(@PathVariable long id);
}
