package com.neu.bsds.linyu.Server;

import com.neu.bsds.linyu.Client.SkierData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
//    @Path("myresource")
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getIt() {
//        return "Got it!!";
//    }
//
//    @Path("myresource")
//    @POST
//    @Consumes(MediaType.TEXT_PLAIN)
//    public int postText(String content) {
//        return content.length();
//    }

    @Path("myvert/{skierID}&{dayNum}")
    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getData(@PathParam("skierID") String skierID, @PathParam("dayNum") String dayNum) {
        return skierID + dayNum;
    }

    @Path("load")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public String postData(SkierData sd) {
        return "get your raw data";
    }
}