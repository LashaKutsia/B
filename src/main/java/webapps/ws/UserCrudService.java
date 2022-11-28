package webapps.ws;

import webapps.model.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public interface UserCrudService {

    @GET
    @Path("/getinfo")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response userById(@QueryParam("Id") String Id);

    @PUT
    @Path("/fillBalance")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response fillBalance(Transaction transaction);


}
