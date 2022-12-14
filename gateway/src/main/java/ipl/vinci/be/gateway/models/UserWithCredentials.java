package ipl.vinci.be.gateway.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserWithCredentials {


    private String email;
    private String firstname;
    private String lastname;
    private String password;


    public Credentials toCredentials() {
        return new Credentials(email, password);
    }
    public NewUser toNewUser(){
        return new NewUser(email,firstname,lastname,password);
    }
}