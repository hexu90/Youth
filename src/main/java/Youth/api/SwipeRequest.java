package Youth.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@JsonDeserialize(builder= SwipeRequest.SwipeRequestBuilder.class)
public class SwipeRequest {
    @NotNull private long viewedUserId;
    private boolean isLikeOrNot;

    @JsonPOJOBuilder(withPrefix = "")
    public static class SwipeRequestBuilder {
        boolean isLikeOrNot = false;
    }
}
