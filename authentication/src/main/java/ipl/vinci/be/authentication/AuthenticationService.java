package ipl.vinci.be.authentication;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import ipl.vinci.be.authentication.models.Credentials;
import ipl.vinci.be.authentication.models.InsecureCredentials;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationRepository repository;
    private final Algorithm jwtAlgorithm;
    private final JWTVerifier jwtVerifier;

    public AuthenticationService(AuthenticationRepository repository, AuthenticationProperties properties) {
        this.repository = repository;
        this.jwtAlgorithm = Algorithm.HMAC512(properties.getSecret());
        this.jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();
    }


    /**
     * Connects user with credentials
     * @param insecureCredentials The credentials with insecure password
     * @return The JWT token, or null if the user couldn't be connected
     */
    public String connect(InsecureCredentials insecureCredentials) {
        Credentials credentials = repository.findById(insecureCredentials.getEmail()).orElse(null);
        if (credentials == null) return null;
        if (!BCrypt.checkpw(insecureCredentials.getPassword(), credentials.getPassword())) return null;
        return JWT.create().withIssuer("auth0").withClaim("pseudo", credentials.getEmail()).sign(jwtAlgorithm);
    }


    /**
     * Verifies JWT token
     * @param token The JWT token
     * @return The pseudo of the user, or null if the token couldn't be verified
     */
    public String verify(String token) {
        try {
            String pseudo = jwtVerifier.verify(token).getClaim("pseudo").asString();
            if (!repository.existsById(pseudo)) return null;
            return pseudo;
        } catch (JWTVerificationException e) {
            return null;
        }
    }


    /**
     * Creates credentials in repository
     * @param insecureCredentials The credentials with insecure password
     * @return True if the credentials were created, or false if they already exist
     */
    public boolean createOne(InsecureCredentials insecureCredentials) {
        if (repository.existsById(insecureCredentials.getEmail())) return false;
        String hashedPassword = BCrypt.hashpw(insecureCredentials.getPassword(), BCrypt.gensalt());
        repository.save(insecureCredentials.toCredentials(hashedPassword));
        return true;
    }

    /**
     * Updates credentials in repository
     * @param insecureCredentials The credentials with insecure password
     * @return True if the credentials were updated, or false if they couldn't be found
     */
    public boolean updateOne(InsecureCredentials insecureCredentials) {
        if (!repository.existsById(insecureCredentials.getEmail())) return false;
        String hashedPassword = BCrypt.hashpw(insecureCredentials.getPassword(), BCrypt.gensalt());
        repository.save(insecureCredentials.toCredentials(hashedPassword));
        return true;
    }

    /**
     * Deletes credentials in repository
     * @param pseudo The pseudo of the user
     * @return True if the credentials were deleted, or false if they couldn't be found
     */
    public boolean deleteOne(String pseudo) {
        if (!repository.existsById(pseudo)) return false;
        repository.deleteById(pseudo);
        return true;
    }

}
