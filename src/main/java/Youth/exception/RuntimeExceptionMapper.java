package Youth.exception;

import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/*
* Map hibernate sql exception
*  */
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException e) {
        JSONObject response = new JSONObject();
        response.put("errorMessage", e.getMessage());
        return Response.status(500)
                .entity(response.toString())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
