package com.neu.bsds.linyu.Client.PostNGetClient.GetClient;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;
import com.neu.bsds.linyu.Client.Metrics.Metrics;

import java.util.ArrayList;
import java.util.List;

import static com.neu.bsds.linyu.Client.PostNGetClient.PostClient.StartPostClient.SplitRawData;
import static com.neu.bsds.linyu.Client.PostNGetClient.PostClient.StartPostClient.getMetrics;

/**
 * Created by linyuyu on 10/19/17.
 */
public class StartGetClient {

    private static String ipAddress = "bsds-1332599737.us-west-2.elb.amazonaws.com";
    private static String port = "8080";
    private static int threadNum = 100;

    public static void main(String[] args) {

        if (args.length > 0) {
            threadNum = Integer.parseInt(args[0]);
            ipAddress = args[1];
            port = args[2];
        }
        System.out.println("ip address is: " + ipAddress);
        System.out.println("port is: " + port);
        System.out.println("thread number is: " + threadNum);

        String url = "http://" + ipAddress + ":" + port + "/assignment2";
        List<Metrics> metrics = new ArrayList<Metrics>();

        List<SingleRideData> singleRideDataList = new ArrayList<>();
        createRawData(singleRideDataList);
        List<List<SingleRideData>> listOfThreadData = new ArrayList<>();
        SplitRawData(singleRideDataList, threadNum, listOfThreadData);

        MultiThreadGetClient mc = new MultiThreadGetClient(url, threadNum, metrics, listOfThreadData);
        mc.runMultithread();
        getMetrics(metrics);
    }

    public static void createRawData(List<SingleRideData> singleRideDataList) {
//        for (int i = 0; i < 40000; i++) {
//            SingleRideData sd = new SingleRideData("1", String.valueOf(i + 1));
//            singleRideDataList.add(sd);
//        }

        for (int i = 0; i < 3000; i++) {
            SingleRideData sd = new SingleRideData("999", String.valueOf(i + 1));
            singleRideDataList.add(sd);
        }
    }
}
