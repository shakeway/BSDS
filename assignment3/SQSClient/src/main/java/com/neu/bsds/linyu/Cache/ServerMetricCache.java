package com.neu.bsds.linyu.Cache;

import com.neu.bsds.linyu.DataType.ServerMetric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by linyuyu on 11/20/17.
 */
public class ServerMetricCache {

    private static List<ServerMetric> ServerMetricCacheList = Collections.synchronizedList(new ArrayList<ServerMetric>());

    public synchronized static void add (int responseTime, int errorNum, int flag) {
        ServerMetricCacheList.add(new ServerMetric(responseTime, errorNum, flag));
    }


    public synchronized static int getSize () {
        return ServerMetricCacheList.size();
    }

    public synchronized static List<ServerMetric> getMetriCacheList () {
        List<ServerMetric> res = ServerMetricCacheList;
        //ServerMetricCacheList = Collections.synchronizedList(new ArrayList<ServerMetric>());
        return res;
    }

    public synchronized static void  cleanMetricCacheList () {
        ServerMetricCacheList = Collections.synchronizedList(new ArrayList<ServerMetric>());
    }
}
