package com.neu.bsds.linyu.Server;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;
import com.neu.bsds.linyu.Client.DataType.SummaryRideData;

//import javax.servlet.ServletContext;
import javax.ws.rs.*;
//import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
//import java.io.WriteAbortedException;
import java.sql.SQLException;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class MyResource {

//    @Context
//    private ServletContext context;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @Path("myresource")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @Path("myresource")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public int postText(String content) {
        return (content.length());
    }

    @Path("myvert/{skierID}&{dayNum}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //Produces(MediaType.TEXT_PLAIN)
    public SummaryRideData getData(@PathParam("skierID") String skierID, @PathParam("dayNum") String dayNum) throws SQLException {
        return SummaryRideDAO.get(skierID, dayNum);
    }

    @Path("load")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public String postData(SingleRideData sd) throws SQLException {
        System.out.println(sd.toString());
        postAndUpdate(sd);
        return sd.toString();
    }

    private void postAndUpdate(SingleRideData sd) throws SQLException {
//        SingleRideDAO singleRideDAO = SingleRideDAO.getInstance();
        SingleRideDAO.insert(sd);
//        SummaryRideDAO summaryRideDAO = SummaryRideDAO.getInstance();
//        summaryRideDAO.update(sd);
    }

//    private void postWithCache(SingleRideDAO sd) throws SQLException {
//        PostCache postCache = PostCache.getInstance();
//        postCache.addToCache(sd);
//        WriteCache writeCache = WriteCache.getInstance();
//        writeCache.addToCache(sd);
//    }

}
