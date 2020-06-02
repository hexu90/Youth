package Youth.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "geo_index")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = GeoIndex.GeoIndexBuilder.class)
public class GeoIndex {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "geo_hash")
    private String geoHash;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GeoIndexBuilder {
    }
}
