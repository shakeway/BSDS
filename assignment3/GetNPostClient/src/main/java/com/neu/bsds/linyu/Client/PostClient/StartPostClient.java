package com.neu.bsds.linyu.Client.PostClient;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;
import com.neu.bsds.linyu.Client.Metrics.Metrics;
import com.neu.bsds.linyu.Client.PostClient.MultiThreadPostClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by linyuyu on 10/19/17.
 */
public class StartPostClient {

    private static String ipAddress = "bsds-1332599737.us-west-2.elb.amazonaws.com";
    //private static String ipAddress = "localhost";
    private static String port = "8080";
    private static int threadNum = 100;

    //Delimiter used in CSV file
    private static String COMMA_DELIMITER = ",";
    private static String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static String FILE_HEADER = "post_latency";

    private static String FILE_NAME = "post_latency_summary.csv";


    public static void main(String[] args) {

        if (args.length > 0) {
            threadNum = Integer.parseInt(args[0]);
            ipAddress = args[1];
            port = args[2];
        }
        System.out.println("ip address is: " + ipAddress);
        System.out.println("port is: " + port);
        System.out.println("thread number is: " + threadNum);

        //String url = "http://" + ipAddress + ":" + port + "/assignment2";
        String url = "http://" + ipAddress + ":" + port;
        List<Metrics> metrics = new ArrayList<Metrics>();

        //read csv file
        List<SingleRideData> singleRideDataList = new ArrayList<SingleRideData>();
        ReadRawData(singleRideDataList);
        List<List<SingleRideData>> listOfThreadData = new ArrayList<>();
        SplitRawData(singleRideDataList, threadNum, listOfThreadData);

        MultiThreadPostClient mc = new MultiThreadPostClient(url, threadNum, metrics, listOfThreadData);
        mc.runMultithread();
        getMetrics(metrics);
    }

    public static void ReadRawData(List<SingleRideData> singleRideDataList) {
        String csvFile = "/Users/linyuyu/MyFile/NEU/courses/BSDS/assignment/assign3/BSDSAssignment2Day999.csv";
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

    public static void exportDataToCSV(Long[] latencyArray) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(FILE_NAME);
            //write the CSV file header
            fileWriter.append(FILE_HEADER.toString());
            //add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);
            //write latency to the CSV file one by one
            for (Long latency: latencyArray) {
                fileWriter.append(String.valueOf(latency));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        exportDataToCSV(latencyArray);

    }
}
