package ipl.vinci.be.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long Id;
    private String email;
    private String firstname;
    private String lastname;
}



