package Youth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "user_match" )
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserMatch {
    @EmbeddedId private UserMatchId id;
}
