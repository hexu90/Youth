package Youth.resources;

import Youth.UserService;
import Youth.api.SwipeRequest;
import Youth.core.YouthException;
import Youth.model.User;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import javax.annotation.security.PermitAll;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Slf4j
@Path("/v0/youth")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class YouthResource {
  private final UserService userService;
  private static final String SAME_USER_ERROR_MSG = "user can not swipe himself";

  @GET
  @Path("/match")
  @UnitOfWork
  @Timed
  @PermitAll
  public Response findMatches(@CookieParam("auth_user") @NotNull long userId) {
    List<User> matches = userService.findAllMatches(userId);
    return Response.status(200).entity(matches).build();
  }

  @POST
  @Path("/swipe")
  @UnitOfWork
  @Timed
  @PermitAll
  public Response swipe(SwipeRequest request, @CookieParam("auth_user") long userId)
      throws YouthException {
    JSONObject response = new JSONObject();
    if (userId == request.getViewedUserId()) {
      response.put("message", SAME_USER_ERROR_MSG);
      return Response.status(400).entity(response).build();
    }
    boolean isMatching =
        userService.swipe(userId, request.getViewedUserId(), request.isLikeOrNot());
    response.put("isMatching", isMatching);

    return Response.status(200).entity(response.toString()).build();
  }

  @GET
  @Path("/recommend")
  @UnitOfWork
  @Timed
  @PermitAll
  public Response recommendUser(
      @QueryParam("lat") double lat,
      @QueryParam("lng") double lng,
      @QueryParam("radius") @DefaultValue("3") int radius,
      @QueryParam("size") @DefaultValue("10") int size,
      @CookieParam("auth_user") Long userId)
      throws YouthException {


    List<User> recommendedUser = userService.recommend(userId, lat, lng, size, radius);
    return Response.status(200).entity(recommendedUser).build();
  }
}
