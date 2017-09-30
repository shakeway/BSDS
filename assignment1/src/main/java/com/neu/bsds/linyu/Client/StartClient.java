package com.neu.bsds.linyu.Client;

import java.util.Arrays;
import java.util.List;

/**
 * Created by linyuyu on 9/28/17.
 */
public class StartClient {
    public static void main(String[] args) {
        String ipAddress = "http://34.208.35.14";
        String port = "8080";
        int threadNum = 10;
        int iterationNum = 100;

        if (args.length == 2) {
            ipAddress = args[0];
            port = args[1];
        } else {
            threadNum = Integer.parseInt(args[0]);
            iterationNum = Integer.parseInt(args[1]);
            ipAddress = args[2];
            port = args[3];
        }
        System.out.println("ip address is: " + ipAddress);
        System.out.println("port is: " + port);
        System.out.println("iteration number is: " + iterationNum);
        System.out.println("thread number is: " + threadNum);

        String url = "http://" + ipAddress + ":" + port;
        Metrics metrics = new Metrics();
        MultithreadClient mc = new MultithreadClient(url, threadNum, iterationNum, metrics);
        mc.runMultithread();
        getMetrics(metrics);
    }

    public static void getMetrics(Metrics metrics) {

        System.out.println("Total number of request sent: " + metrics.getRequestCount());
        System.out.println("Total number of successful reponses: " + metrics.getSuccessCount());

        int latencySum = 0;
        List<Long> latencyList = metrics.getLatencyList();
        Long[] latencyArray = latencyList.toArray(new Long[latencyList.size()]);
        int size = latencyList.size();

        for (long l: latencyList) {
            latencySum += l;
        }

        double meanLatency = latencySum / size;
        System.out.println("Mean latencies for all requests is: " + meanLatency + " ms");

        Arrays.sort(latencyArray);
        double mediaLatency;
        if (size %2 == 0) {
            mediaLatency = (latencyArray[size / 2 - 1] + latencyArray[size / 2 ]) / 2;
        } else {
            mediaLatency = latencyArray[size / 2];
        }
        System.out.println("Median latencies for all request is: " + mediaLatency + " ms");

        long lt99 = latencyArray[(int) (0.99 * size - 1)];
        long lt95 = latencyArray[(int) (0.95 * size - 1)];

        System.out.println("95th percentile latency is: " + lt95 + " ms");
        System.out.println("99th percentile latency is: " + lt99 + " ms");
        System.out.println("task done");

    }
}
