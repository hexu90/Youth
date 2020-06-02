package Youth.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.persistence.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = User.UserBuilder.class)
public class User implements Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lng")
    private double lng;

//    @OneToMany(
//            mappedBy = "primaryUser",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    @JsonManagedReference
//    private List<UserIntention> userIntentions;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "email")
    private String email;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {
        List<UserIntention> userIntentions = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User tag = (User) o;
        return Objects.equals(name, tag.name);
    }

//    @Override
//    public String toString() {
//        return null;
//    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

//    @Override
//    public boolean implies(Subject subject) {
//        return false;
//    }
}
