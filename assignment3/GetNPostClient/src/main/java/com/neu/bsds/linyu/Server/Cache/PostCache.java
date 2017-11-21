package com.neu.bsds.linyu.Server.Cache;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by linyuyu on 10/26/17.
 */

public class PostCache {

    private static List<SingleRideData> cacheList = Collections.synchronizedList(new ArrayList<SingleRideData>());

    public synchronized static void addToCache(SingleRideData sd) {
        cacheList.add(sd);
    }

    public synchronized static List<SingleRideData> getCacheList() {
        List<SingleRideData> res = cacheList;
        res = Collections.synchronizedList(new ArrayList<SingleRideData>());
        return res;
    }

    public synchronized static int getSize() {
        return cacheList.size();
    }

    public synchronized static void cleanCache() {
        cacheList = Collections.synchronizedList(new ArrayList<SingleRideData>());
    }
}
