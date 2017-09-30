package com.neu.bsds.linyu.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linyuyu on 9/28/17.
 */
public class Metrics {
    private List<Long> latencyList = new ArrayList<>();
    private int requestCount = 0;
    private int successCount = 0;

    public synchronized void add (long latency) {
        latencyList.add(latency);
    }

    public synchronized void requestAdd () {
        requestCount++;
    }

    public synchronized void successAdd() {
        successCount++;
    }

    public synchronized List<Long> getLatencyList() {
        return latencyList;
    }

    public synchronized int getRequestCount() {
        return requestCount;
    }

    public synchronized int getSuccessCount() {
        return successCount;
    }
}
