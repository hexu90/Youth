package Youth.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserIntentionId that = (UserIntentionId) o;
        return Objects.equals(primaryUserId, that.primaryUserId) &&
                Objects.equals(viewedUserId, that.viewedUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryUserId, viewedUserId);
    }
}
