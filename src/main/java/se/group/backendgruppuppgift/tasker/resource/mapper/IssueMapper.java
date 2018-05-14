package se.group.backendgruppuppgift.tasker.resource.mapper;

import se.group.backendgruppuppgift.tasker.service.exception.IssueException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;


@Provider
public final class IssueMapper implements ExceptionMapper<IssueException> {

    @Override
    public Response toResponse(IssueException exception) {
        return Response.status(BAD_REQUEST).entity(singletonMap("ERROR", exception.getMessage())).build();
    }
}