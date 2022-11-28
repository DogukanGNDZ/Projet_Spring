package ipl.vinci.be.gateway.models;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewUser {
    private String email;
    private String firstname;
    private String lastname;
    private String password;
}
