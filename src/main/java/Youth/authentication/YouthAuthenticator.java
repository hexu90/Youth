package Youth.authentication;

import Youth.dao.UserDAO;
import Youth.model.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class YouthAuthenticator implements Authenticator<CustomCredentials, User> {
    private UserDAO userDAO;

    @Override
    @UnitOfWork
    public Optional<User> authenticate(CustomCredentials credentials) throws AuthenticationException {
        User user = userDAO.findByUserId(credentials.getUserId());
        return Optional.ofNullable(user);
    }
}
