package Youth.authentication;

import Youth.dao.UserDAO;
import Youth.model.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class YouthBasicAuthenticator implements Authenticator<BasicCredentials, User> {
    private UserDAO userDAO;

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        if ("secret".equals(basicCredentials.getPassword())) {
      return Optional.of(User.builder().name(basicCredentials.getUsername()).build());
        }
        return Optional.empty();
    }
}
