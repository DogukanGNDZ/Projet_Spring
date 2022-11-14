package ipl.vinci.be.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ipl.vinci.be.authentication")
@Getter
@Setter
public class AuthenticationProperties {

    private String secret;

}
