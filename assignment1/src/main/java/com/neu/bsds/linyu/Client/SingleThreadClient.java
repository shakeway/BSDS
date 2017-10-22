package com.neu.bsds.linyu.Client;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Created by linyuyu on 9/28/17.
 */
public class SingleThreadClient extends Thread{


    //private String url;
    private int iterationNum;
    private Metrics metrics;
    private MyClient myClient;
    private WebTarget webTarget;

    public SingleThreadClient(WebTarget webTarget, int iterationNum, Metrics metrics) {
        this.webTarget = webTarget;
        this.iterationNum = iterationNum;
        this.metrics = metrics;
        this.myClient = new MyClient(this.webTarget);
    }

    @Override
    public void run() {

        for (int i = 0; i < iterationNum; i++) {

            //post
            long startTime = System.currentTimeMillis();
            Response postResponse = myClient.postText("test");
            long currentime = System.currentTimeMillis();
            metrics.add(currentime - startTime);
            metrics.requestAdd();
            if (postResponse.readEntity(String.class).length() == 4) {
                metrics.successAdd();
                //System.out.println("request num: " + metrics.getRequestCount());
            }
            postResponse.close();

            //get
            startTime = System.currentTimeMillis();
            String getResponse = myClient.getStatus();
            currentime = System.currentTimeMillis();
            metrics.add(currentime - startTime);
            metrics.requestAdd();
            if (getResponse.equals("Got it!")) {
                metrics.successAdd();
                //System.out.println("request num: " + metrics.getRequestCount());
            }

        }

    }

}
