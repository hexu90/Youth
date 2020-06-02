package Youth.authentication;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CustomCredentials {
    private Long userId;
    private UUID token;
}
