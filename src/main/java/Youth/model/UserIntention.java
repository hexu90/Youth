package Youth.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "user_intention")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserIntention {

  @EmbeddedId private UserIntentionId id;

  @Column(name = "like_or_dislike")
  private Boolean likeOrDislike;
}
