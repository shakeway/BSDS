package com.neu.bsds.linyu.Client;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Created by linyuyu on 10/19/17.
 */
public class MyClient {

    private static WebTarget webTarget;

    public MyClient(WebTarget webTarget) {
        this.webTarget = webTarget;
    }

    public Response postData(SingleRideData sd) throws ClientErrorException {
        WebTarget resource = webTarget.path("assignment2/webapi/load");
        //WebTarget resource = webTarget.path("webapi/load");
        return resource.request().post(Entity.json(sd));
    }

    public Response getData(SingleRideData sd) throws ClientErrorException {
        WebTarget resource = webTarget.path("assignment2/webapi/myvert/" + sd.getSkierID() + "&" + sd.getDayNum());
        return resource.request().get();
    }
}
