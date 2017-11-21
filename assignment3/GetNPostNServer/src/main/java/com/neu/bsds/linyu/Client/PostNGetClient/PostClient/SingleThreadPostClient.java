package com.neu.bsds.linyu.Client.PostNGetClient.PostClient;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;
import com.neu.bsds.linyu.Client.Metrics.Metrics;
import com.neu.bsds.linyu.Client.PostNGetClient.MyClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by linyuyu on 10/19/17.
 */
public class SingleThreadPostClient extends Thread {

    private Metrics metrics;
    private MyClient myClient;
    private WebTarget webTarget;
    private List<SingleRideData> singleRideDataList;

    public SingleThreadPostClient(WebTarget webTarget, Metrics metrics, List<SingleRideData> singleRideDataList) {
        this.webTarget = webTarget;
        this.metrics = metrics;
        this.myClient = new MyClient(this.webTarget);
        this.singleRideDataList = singleRideDataList;
    }

    @Override
    public void run() {
        for (int i = 0; i < singleRideDataList.size(); i++) {

            //post
            long startTime = System.currentTimeMillis();
            Response postResponse = myClient.postData(singleRideDataList.get(i));
            long currentime = System.currentTimeMillis();
            metrics.add(currentime - startTime);
            metrics.requestAdd();
            if (postResponse.getStatus() == 200) {
                metrics.successAdd();
            }
            postResponse.close();

        }
    }
}
