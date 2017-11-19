package com.neu.bsds.linyu.Client.GetClient;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;
import com.neu.bsds.linyu.Client.Metrics.Metrics;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by linyuyu on 10/20/17.
 */
public class MultiThreadGetClient {
    private String url;
    private int threadNum;
    private List<Metrics> metrics;
    private List<List<SingleRideData>> listOfThreadData;

    public MultiThreadGetClient(String url, int threadNum, List<Metrics> metrics, List<List<SingleRideData>> listOfThreadData) {
        this.url = url;
        this.threadNum = threadNum;
        this.metrics = metrics;
        this.listOfThreadData = listOfThreadData;
    }

    public void runMultithread() {
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        System.out.println("Client starting");
        Long startTime = System.currentTimeMillis();
        System.out.println(String.format("start at: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(startTime)));

        //create and start client threads
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url);
        for (int i = 0; i < threadNum; i++) {
            Metrics mt = new Metrics();
            metrics.add(mt);
            SingleThreadGetClient worker = new SingleThreadGetClient(webTarget, mt, listOfThreadData.get(i));
            executor.submit(worker);
        }

        System.out.println("All "+ threadNum + " threads running");
        executor.shutdown();
        while(!executor.isTerminated());

        Long endTime = System.currentTimeMillis();
        System.out.println(String.format("All threads complete at: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(endTime)));
        Long wallTime = endTime - startTime;
        System.out.println("Wall Time: " + wallTime + " ms");
    }
}
