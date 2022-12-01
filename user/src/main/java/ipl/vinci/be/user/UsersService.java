package ipl.vinci.be.user;

import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository repository;

    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a user
     * @param user User to create
     * @return true if the user could be created, false if another user exists with this pseudo
     */
    public User createOne(NewUser user) {
        if (repository.existsByEmail(user.getEmail())) return null;
        return repository.save(user.toUser());
    }

    /**
     *
     * @param email
     * @return the user with his email
     */
    public User readOneByEmail(String email){
        return repository.findByEmail(email);
    }

    public User readOneById(long id){
        return repository.findById(id).orElse(null);
    }


    public boolean updateOne(User user){
        if(!repository.existsById(user.getId())) return false;
        repository.save(user);
        return true;
    }

    public boolean deleteOne(long id){
        if(!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }



}
