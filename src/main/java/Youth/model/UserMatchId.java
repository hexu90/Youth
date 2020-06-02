package Youth.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMatchId implements Serializable {
    @Column(name = "primary_user_id")
    private Long primaryUserId;

    @Column(name = "matched_user_id")
    private Long matchedUserId;
}
