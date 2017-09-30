package com.neu.bsds.linyu.Client;

/**
 * Created by linyuyu on 9/28/17.
 */
public class SingleThreadClient extends Thread{


    private String url;
    private int iterationNum;
    private Metrics metrics;
    private MyClient myClient;

    public SingleThreadClient(String url, int iterationNum, Metrics metrics) {
        this.url = url;
        this.iterationNum = iterationNum;
        this.metrics = metrics;
        this.myClient = new MyClient(url);
    }

    @Override
    public void run() {

        for (int i = 0; i < iterationNum; i++) {

            //post
            long startTime = System.currentTimeMillis();
            int postResponse = myClient.postText("test", Integer.class);
            long currentime = System.currentTimeMillis();
            metrics.add(currentime - startTime);
            metrics.requestAdd();
            if (postResponse == 4) {
                metrics.successAdd();
                //System.out.println("request num: " + metrics.getRequestCount());
            }

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
