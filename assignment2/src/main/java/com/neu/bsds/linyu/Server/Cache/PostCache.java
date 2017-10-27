package com.neu.bsds.linyu.Server.Cache;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;

//import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by linyuyu on 10/26/17.
 */

//@Singleton
public class PostCache {
    private static PostCache instance;
    private static List<SingleRideData> cacheList;

    public PostCache() {
        cacheList = Collections.synchronizedList(new ArrayList<SingleRideData>());
    }

    public static PostCache getInstance () {
        if (instance == null) {
            instance = new PostCache();
        }
        return instance;
    }

    public synchronized void addToCache(SingleRideData sd) {
        cacheList.add(sd);
    }

    public synchronized List<SingleRideData> getCacheList() {
        List<SingleRideData> res = cacheList;
        res = Collections.synchronizedList(new ArrayList<SingleRideData>());
        return cacheList;
    }

    public synchronized int getSize() {
        return cacheList.size();
    }
}
