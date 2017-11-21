package com.neu.bsds.linyu.Server.Cache;

import com.neu.bsds.linyu.Server.DataType.ServerMetric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by linyuyu on 11/21/17.
 */
public class ServerMetricCache {
    private static List<ServerMetric> metricCacheList = Collections.synchronizedList(new ArrayList<ServerMetric>());

    public synchronized static void addMetric (ServerMetric serverMetric) {
        metricCacheList.add(serverMetric);
    }


    public synchronized static int getSize () {
        return metricCacheList.size();
    }

    public synchronized static List<ServerMetric> getMessageCacheList () {
        List<ServerMetric> res = metricCacheList;
        metricCacheList = Collections.synchronizedList(new ArrayList<ServerMetric>());
        return res;
    }

}
