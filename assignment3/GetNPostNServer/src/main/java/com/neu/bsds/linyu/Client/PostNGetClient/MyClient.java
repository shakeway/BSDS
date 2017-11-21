package com.neu.bsds.linyu.Client.PostNGetClient;

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
        WebTarget resource = webTarget.path("webapi/load");
        return resource.request().post(Entity.json(sd));
    }

    public Response getData(SingleRideData sd) throws ClientErrorException {
        WebTarget resource = webTarget.path("webapi/myvert/" + sd.getSkierID() + "&" + sd.getDayNum());
        return resource.request().get();
    }

    //just to check the client can send request to server
//    public static void main(String[] argv) {
//        String url = "http://35.161.146.85:8080/assignment2";
//        WebTarget webTarget = ClientBuilder.newClient().target(url);
//        MyClient mc = new MyClient(webTarget);
//        SingleRideData sd = new SingleRideData("0","2","1", 3, "6");
//
//        //test get method
//        Response getResponse = mc.getData(sd);
//        String getResult = getResponse.readEntity(String.class);
//        System.out.println(getResponse.getStatus());
//        System.out.println("get check: " + getResult);
//
//        //test post method
//        Response postResponse = mc.postData(sd);
//        String postResult = postResponse.readEntity(String.class);
//        System.out.println(postResponse.getStatus());
//        System.out.println("post check: " + postResult);
//    }
}
