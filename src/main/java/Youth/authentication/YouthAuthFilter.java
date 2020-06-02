package Youth.authentication;

import Youth.model.User;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@PreMatching
@Priority(Priorities.AUTHENTICATION)
@AllArgsConstructor
public class YouthAuthFilter extends AuthFilter {
    private YouthAuthenticator authenticator;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Optional<User> authenticatedUser;

        try {
            CustomCredentials credentials = getCredentials(requestContext);
            authenticatedUser = authenticator.authenticate(credentials);
        } catch (AuthenticationException e) {
            throw new WebApplicationException("Unable to validate credentials", Response.Status.UNAUTHORIZED);
        }

        if (!authenticatedUser.isPresent()) {
            throw new WebApplicationException("Credentials not valid", Response.Status.UNAUTHORIZED);
        }
    }

    private CustomCredentials getCredentials(ContainerRequestContext requestContext) {
        CustomCredentials credentials = new CustomCredentials();
        log.info(requestContext.getCookies().toString());
        try {
            String rawToken = requestContext
                    .getCookies()
                    .get("auth_token")
                    .getValue();

            String rawUserId = requestContext
                    .getCookies()
                    .get("auth_user")
                    .getValue();

            log.info("Authentication information: ");
            log.info(rawToken);
            log.info(rawUserId);
            credentials.setToken(UUID.fromString(rawToken));
            credentials.setUserId(Long.valueOf(rawUserId));
        } catch (Exception e) {
            throw new WebApplicationException("Unable to parse credentials", Response.Status.UNAUTHORIZED);
        }

        return credentials;
    }
}
