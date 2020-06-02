package Youth.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Setter
@Getter
@Builder
@JsonDeserialize(builder= RecommendUserRequest.RecommendUserRequestBuilder.class)
public class RecommendUserRequest {
    @NotNull
    private Long userId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class RecommendUserRequestBuilder {
        Optional<Long> viewedUserId = Optional.empty();
        Optional<Boolean> isMatching = Optional.empty();
    }
}
