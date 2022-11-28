package ipl.vinci.be.user;

import ipl.vinci.be.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
    boolean existsByEmail(String email);

}
