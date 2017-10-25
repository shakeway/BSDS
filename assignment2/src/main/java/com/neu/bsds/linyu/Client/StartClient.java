package com.neu.bsds.linyu.Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by linyuyu on 10/19/17.
 */
public class StartClient {

    public static void main(String[] args) {
        String ipAddress = "34.208.35.14";
        //String ipAddress = "localhost";
        //String ipAddress = "34.215.68.92"; ip of Frank
        String port = "8080";
        int threadNum = 100;

        if (args.length > 0) {
            threadNum = Integer.parseInt(args[0]);
            ipAddress = args[1];
            port = args[2];
        }
        System.out.println("ip address is: " + ipAddress);
        System.out.println("port is: " + port);
        System.out.println("thread number is: " + threadNum);

        String url = "http://" + ipAddress + ":" + port + "/assignment2Server";
        //String url = "http://" + ipAddress + ":" + port + "/SkieServer"; url of Frank
        List<Metrics> metrics = new ArrayList<Metrics>();

        //read csv file
        List<SingleRideData> singleRideDataList = new ArrayList<SingleRideData>();
        ReadRawData(singleRideDataList);
        List<List<SingleRideData>> listOfThreadData = new ArrayList<>();
        SplitRawData(singleRideDataList, threadNum, listOfThreadData);

        MultiThreadClient mc = new MultiThreadClient(url, threadNum, metrics, listOfThreadData);
        mc.runMultithread();
        getMetrics(metrics);
    }

    public static void ReadRawData(List<SingleRideData> singleRideDataList) {
        String csvFile = "/Users/linyuyu/MyFile/NEU/courses/BSDS/assignment/assign2/BSDSAssignment2Day1.csv";
        String line = "";
        String csvSplitBy = ",";

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                SingleRideData sd = new SingleRideData(data[0], data[1], data[2], Integer.valueOf(data[3]), data[4]);
                singleRideDataList.add(sd);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SplitRawData(List<SingleRideData> singleRideDataList, int threadNum, List<List<SingleRideData>> listOfThreadData) {
        for(int i = 0; i < threadNum; i++) {
            listOfThreadData.add(new ArrayList<SingleRideData>());
        }

        for (int i = 0; i < singleRideDataList.size(); i++) {
            int index = i % threadNum;
            listOfThreadData.get(index).add(singleRideDataList.get(i));
        }
    }

    public static void getMetrics(List<Metrics> metrics) {

        int numRequest = 0;
        int numReponse = 0;
        int latencySum = 0;
        List<Long> latencyList = new ArrayList<Long>();
        for (int i = 0; i < metrics.size(); i++) {
            numRequest += metrics.get(i).getRequestCount();
            numReponse += metrics.get(i).getSuccessCount();
            latencyList.addAll(metrics.get(i).getLatencyList());
            latencySum += metrics.get(i).getLatencySum();
        }
        System.out.println("Total number of request sent: " + numRequest);
        System.out.println("Total number of successful reponses: " + numReponse);

        Long[] latencyArray = latencyList.toArray(new Long[latencyList.size()]);
        int size = latencyArray.length;

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
