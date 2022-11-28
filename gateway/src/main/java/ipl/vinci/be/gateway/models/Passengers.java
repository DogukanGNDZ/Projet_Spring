package ipl.vinci.be.gateway.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Passengers {
    private List<User> pending;
    private List<User> accepted;
    private List<User> refused;

}
