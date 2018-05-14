package se.group.backendgruppuppgift.tasker.resource;

import org.springframework.stereotype.Component;
import se.group.backendgruppuppgift.tasker.model.Issue;
import se.group.backendgruppuppgift.tasker.model.Task;
import se.group.backendgruppuppgift.tasker.model.web.TaskWeb;
import se.group.backendgruppuppgift.tasker.service.TaskService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("tasks")
public final class TaskResource {

    private final TaskService service;
    @Context
    private UriInfo uriInfo;

    public TaskResource(TaskService service) {
        this.service = service;
    }

    @POST
    public Response createTask(TaskWeb task) {
        Task result = service.createTask(task);
        return Response.created(URI.create(uriInfo
                .getAbsolutePathBuilder()
                .path(result.getId().toString())
                .toString()))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateTask(@PathParam("id") Long id, TaskWeb task) {
        return service.updateTask(id, task)
                .map(Response::ok)
                .orElse(Response.status(NOT_FOUND))
                .build();
    }


    @POST
    @Path("{id}/issue")
    public Response createIssue(Issue issue, @PathParam("id") Long id) {

        return service.getTask(id).map(task -> {
            issue.setTask(task);
            Issue result = service.createIssue(issue, id);
            return Response.status(CREATED).header("Location", "tasks/" + service.getTask(id) + "/" + result.getId()).build();
        }).orElse(Response.status(NOT_FOUND).build());


    }
}
