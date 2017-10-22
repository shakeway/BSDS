package com.neu.bsds.linyu.Client;

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

    public Response postData(SkierData sd) throws ClientErrorException {
        WebTarget resource = webTarget.path("webapi/load");
        return resource.request().post(Entity.json(sd));
    }

    public Response getData(SkierData sd) throws ClientErrorException {
        WebTarget resource = webTarget.path("webapi/myvert/" + sd.getSkierID() + "&" + sd.getDayNum());
        return resource.request().get();
    }

    //just to check the client can send request to server
    public static void main(String[] argv) {
        //MyClient mc = new MyClient("http://34.208.35.14:8080");
        //MyClient mc = new MyClient("http://localhost:8080");
        //String url = "http://localhost:8080/";
        String url = "http://34.208.35.14:8080/assignment2Server";
        WebTarget webTarget = ClientBuilder.newClient().target(url);
        MyClient mc = new MyClient(webTarget);
        SkierData sd = new SkierData("0","1","2", "3", "4");

        //test get method
        Response getResponse = mc.getData(sd);
        String getResult = getResponse.readEntity(String.class);
        System.out.println(getResponse.getStatus());
        System.out.println("get check: " + getResult);

        //test post method
        Response postResponse = mc.postData(sd);
        String postResult = postResponse.readEntity(String.class);
        System.out.println(postResponse.getStatus());
        System.out.println("post check: " + postResult);
    }
}
