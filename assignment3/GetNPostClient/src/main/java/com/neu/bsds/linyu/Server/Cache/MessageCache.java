package com.neu.bsds.linyu.Server.Cache;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by linyuyu on 11/20/17.
 */
public class MessageCache {
    private static List<String> messageCacheList = Collections.synchronizedList(new ArrayList<String>());

    public synchronized static void addMessage (String message) {
        messageCacheList.add(message);
    }


    public synchronized static int getSize () {
        return messageCacheList.size();
    }

    public synchronized static List<String> getMessageCacheList () {
        List<String> res = messageCacheList;
        messageCacheList = Collections.synchronizedList(new ArrayList<String>());
        return res;
    }

}
