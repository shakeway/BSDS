package com.neu.bsds.linyu.Client;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by linyuyu on 10/19/17.
 */
public class SingleThreadClient extends Thread {

    private Metrics metrics;
    private MyClient myClient;
    private WebTarget webTarget;
    private List<SingleRideData> singleRideDataList;

    public SingleThreadClient(WebTarget webTarget, Metrics metrics, List<SingleRideData> singleRideDataList) {
        this.webTarget = webTarget;
        this.metrics = metrics;
        this.myClient = new MyClient(this.webTarget);
        this.singleRideDataList = singleRideDataList;
    }

    @Override
    public void run() {
        int iterationNum = 0;

        for (int i = 0; i < singleRideDataList.size(); i++) {

            //post
            long startTime = System.currentTimeMillis();
            Response postResponse = myClient.postData(singleRideDataList.get(i));
            long currentime = System.currentTimeMillis();
            metrics.add(currentime - startTime);
            metrics.requestAdd();
            if (postResponse.getStatus() == 200) {
                metrics.successAdd();
                System.out.println("request num: " + metrics.getRequestCount());
            }
            postResponse.close();

        }
        System.out.println("one hread done");
    }
}
