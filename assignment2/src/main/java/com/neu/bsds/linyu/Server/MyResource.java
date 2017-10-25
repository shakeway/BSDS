package com.neu.bsds.linyu.Server;

import com.neu.bsds.linyu.Client.SingleRideData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */

    @Path("myvert/{skierID}&{dayNum}")
    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getData(@PathParam("skierID") String skierID, @PathParam("dayNum") String dayNum) throws SQLException {
        return SummaryRideDAO.get(skierID, dayNum);
    }

    @Path("load")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public String postData(SingleRideData sd) throws SQLException {
        SingleRideDAO.insert(sd);
        return "get your raw data";
    }

}
