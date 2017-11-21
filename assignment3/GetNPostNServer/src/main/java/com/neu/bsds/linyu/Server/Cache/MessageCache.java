package com.neu.bsds.linyu.Server.Cache;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by linyuyu on 11/20/17.
 */
public class MessageCache {
    private static List<String> getMessageCacheList = Collections.synchronizedList(new ArrayList<String>());
    private static List<String> postMessageCacheList = Collections.synchronizedList(new ArrayList<String>());

    public synchronized static void addGetMessage (String message) {
        getMessageCacheList.add(message);
    }

    public synchronized static void addPostMessage (String message) {
        postMessageCacheList.add(message);
    }

    public synchronized static int GetMessageCacheListSize () {
        return getMessageCacheList.size();
    }

    public synchronized static int PostMessageCacheListSize () {
        return postMessageCacheList.size();
    }

    public synchronized static List<String> getGetMessageCacheList () {
        List<String> res = getMessageCacheList;
        res = Collections.synchronizedList(new ArrayList<String>());
        return res;
    }

    public synchronized static List<String> getPostMessageCacheList () {
        List<String> res = postMessageCacheList;
        res = Collections.synchronizedList(new ArrayList<String>());
        return res;
    }

    public synchronized static void  cleanGetMessageCacheList () {
        getMessageCacheList = Collections.synchronizedList(new ArrayList<String>());
    }

    public synchronized static void cleanPostMessageCacheList () {
        postMessageCacheList = Collections.synchronizedList(new ArrayList<String>());
    }

}
