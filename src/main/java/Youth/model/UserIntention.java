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
  //    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  //    @MapsId("primaryUserId")
  //    @JoinColumn(name = "primary_user_id")
  //    @JsonBackReference
  //    private User primaryUser
  //  @Column(name = "primary_user_id")
  //  private Long primaryUser;

  //  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  //  @MapsId("viewedUserId")
  //  @JoinColumn(name = "viewed_user_id")
  //  @JsonBackReference
  //  private User viewedUser;
  //  @Column(name = "viewed_user_id")
  //  private Long viewedUserId;

  @Column(name = "like_or_dislike")
  private Boolean likeOrDislike;

  @Column(name = "isMatching")
  private Boolean isMatching;

  //  @Override
  //  public boolean equals(Object o) {
  //    if (this == o) return true;
  //
  //    if (o == null || getClass() != o.getClass()) return false;
  //
  //    UserIntention that = (UserIntention) o;
  //    return Objects.equals(primaryUser, that.primaryUser)
  //        && Objects.equals(viewedUser, that.viewedUser);
  //  }
  //
  //  @Override
  //  public int hashCode() {
  //    return Objects.hash(primaryUser, viewedUser);
  //  }
}
