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

  @GET
  @Path("/match")
  @UnitOfWork
  @Timed
  public Response findMatches(@CookieParam("auth_user") @NotNull long userId)
      throws YouthException {
    List<User> matches = userService.findAllMatches(userId);
    return Response.status(200).entity(matches).build();
  }

  @POST
  @Path("/swipe")
  @UnitOfWork
  @Timed
  public Response swipe(SwipeRequest request, @CookieParam("auth_user") Long userId)
      throws YouthException {
    JSONObject object = new JSONObject();
    boolean isMatching =
        userService.swipe(userId, request.getViewedUserId(), request.isLikeOrNot());
    object.put("isMatching", isMatching);

    return Response.status(200).entity(object.toString()).build();
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
    log.info("cookie user id");
    log.info("{}", userId);

    List<User> recommendedUser = userService.recommend(userId, lat, lng, size, radius);
    return Response.status(200).entity(recommendedUser).build();
  }
}
