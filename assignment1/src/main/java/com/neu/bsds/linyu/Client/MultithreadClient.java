package com.neu.bsds.linyu.Client;

import java.text.SimpleDateFormat;
import java.util.concurrent.*;

/**
 * Created by linyuyu on 9/28/17.
 */
public class MultithreadClient {

    private String url;
    private int threadNum;
    private int iterationNum;
    private int requestCount;
    private int successCount;
    private Metrics metrics;

    //static CyclicBarrier barrier;

    public MultithreadClient(String url, int threadNum, int iterationNum, Metrics metrics) {
        this.url = url;
        this.threadNum = threadNum;
        this.iterationNum = iterationNum;
        this.metrics = metrics;
    }

    public void runMultithread() {

        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        System.out.println("Client starting");
        Long startTime = System.currentTimeMillis();
        System.out.println(String.format("start at: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(startTime)));

        //create and start client threads
        SingleThreadClient worker = new SingleThreadClient(url, iterationNum, metrics);
        for (int i = 0; i < threadNum; i++) {
            //Thread worker = new Thread(new SingleThreadClient(url, iterationNum, metrics));
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
