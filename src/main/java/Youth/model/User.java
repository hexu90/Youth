package Youth.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.persistence.*;
import java.security.Principal;

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

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "email")
    private String email;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {
    }
}
