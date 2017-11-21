package com.neu.bsds.linyu.Client.GetClient;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;
import com.neu.bsds.linyu.Client.Metrics.Metrics;
import com.neu.bsds.linyu.Client.MyClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by linyuyu on 10/19/17.
 */
public class SingleThreadGetClient extends Thread {

    private Metrics metrics;
    private MyClient myClient;
    private WebTarget webTarget;
    private List<SingleRideData> singleRideDataList;

    public SingleThreadGetClient(WebTarget webTarget, Metrics metrics, List<SingleRideData> singleRideDataList) {
        this.webTarget = webTarget;
        this.metrics = metrics;
        this.myClient = new MyClient(this.webTarget);
        this.singleRideDataList = singleRideDataList;
    }

    @Override
    public void run() {
        for (int i = 0; i < singleRideDataList.size(); i++) {

            //get
            long startTime = System.currentTimeMillis();

            //change the dayNum to the previous dayNum
            SingleRideData sd = singleRideDataList.get(i);
            SingleRideData targetSD = new SingleRideData(
                    sd.getResortID(),
                    String.valueOf(Integer.valueOf(sd.getDayNum())-1),
                    sd.getSkierID(),
                    sd.getLiftID(),
                    sd.getTimestamp()
            );

            Response getResponse = myClient.getData(targetSD);
            long currentime = System.currentTimeMillis();
            metrics.add(currentime - startTime);
            metrics.requestAdd();
            if (getResponse.getStatus() == 200) {
                metrics.successAdd();
            }
            getResponse.close();

        }
    }
}
