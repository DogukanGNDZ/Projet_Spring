package ipl.vinci.be.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UsersController {

    private final UsersService service;

    public UsersController(UsersService service) {
        this.service = service;
    }

    @PostMapping("/users")
    public User createOne(@RequestBody NewUser user){
        if (user.getEmail() == null || user.getLastname() == null || user.getFirstname()==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User created = service.createOne(user);
        if(created==null)throw new ResponseStatusException(HttpStatus.CONFLICT);
        return created;
    }

    @GetMapping("/users")
    public User readOneByEmail (@RequestParam String email){
        User user = service.readOneByEmail(email);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return user;
    }

    @GetMapping("/users/{id}")
    public User readOneById (@PathVariable long id){
        User user = service.readOneById(id);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return user;
    }
    @PutMapping("/users/{id}")
    public void updateOne(@PathVariable long id, @RequestBody User user) {
        if (user.getId() != id || user.getEmail() == null || user.getFirstname() == null || user.getLastname()==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean found = service.updateOne(user);
        if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/{id}")
    public void deleteOne(@PathVariable long id){
        boolean found = service.deleteOne(id);
        if(!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


}
