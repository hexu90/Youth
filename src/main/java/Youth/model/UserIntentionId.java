package Youth.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserIntentionId implements Serializable {
    @Column(name = "primary_user_id")
    private Long primaryUserId;

    @Column(name = "viewed_user_id")
    private Long viewedUserId;
}
