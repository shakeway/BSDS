package com.neu.bsds.linyu.Client;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by linyuyu on 9/27/17.
 */
public class MyClient {

    private String url;
    private static WebTarget webTarget;

    public MyClient(String url) {
        this.url = url;
        Client client = ClientBuilder.newClient();
         this.webTarget = client.target(url).path("assign1/webapi/myresource");
    }

    public <T> T postText(Object requestEntity, Class<T> responseType) throws ClientErrorException {
        return webTarget.request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(requestEntity, MediaType.TEXT_PLAIN), responseType);
    }

    public String getStatus() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public static void main(String[] argv) {
        MyClient mc = new MyClient("http://34.208.35.14:8080");
        Response getResponse = mc.webTarget.request(MediaType.TEXT_PLAIN).get();
        String getResult = getResponse.readEntity(String.class);
        System.out.println(getResponse.getStatus());
        System.out.println("get check: " + getResult);

        Response postResponse = mc.webTarget.request(MediaType.TEXT_PLAIN).post(Entity.entity("test", MediaType.TEXT_PLAIN));
        String postResult = postResponse.readEntity(String.class);
        System.out.println(postResponse.getStatus());
        System.out.println("post check: " + postResult);
    }

}
