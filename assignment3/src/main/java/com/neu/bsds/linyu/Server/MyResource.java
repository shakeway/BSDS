package com.neu.bsds.linyu.Server;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;
import com.neu.bsds.linyu.Client.DataType.SummaryRideData;
import com.neu.bsds.linyu.Server.Cache.Cacher;
import com.neu.bsds.linyu.Server.Cache.PostCache;
import com.neu.bsds.linyu.Server.DAO.SingleRideDAO;
import com.neu.bsds.linyu.Server.DAO.SummaryRideDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class MyResource {

    static {
        Cacher.start();
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */

    @Path("myvert/{skierID}&{dayNum}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SummaryRideData getData(@PathParam("skierID") String skierID, @PathParam("dayNum") String dayNum) throws SQLException {
        return SummaryRideDAO.get(skierID, dayNum);
    }

    @Path("load")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String postData(SingleRideData sd) throws SQLException {
        System.out.println(sd.toString());
        //postAndUpdate(sd);
        postWithCache(sd);
        return sd.toString();
    }

    private void postAndUpdate(SingleRideData sd) throws SQLException {
        SingleRideDAO.insert(sd);
        SummaryRideDAO.update(sd);
    }

    private void postWithCache(SingleRideData sd) throws SQLException {
        PostCache.addToCache(sd);
    }

}
