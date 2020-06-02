package Youth.exception;

import Youth.core.YouthException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class YouthExceptionMapper implements ExceptionMapper<YouthException> {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Override
  public Response toResponse(YouthException e) {
    return Response.status(e.getCode())
        .entity(e.getMessage())
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}
