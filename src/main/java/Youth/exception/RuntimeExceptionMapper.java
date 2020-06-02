package Youth.exception;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/*
* Map hibernate sql exception
*  */
@Slf4j
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException e) {
        log.error(e.getStackTrace().toString());
        JSONObject response = new JSONObject();
        response.put("errorMessage", e.getMessage());
        return Response.status(500)
                .entity(response.toString())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
