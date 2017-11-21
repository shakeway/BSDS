package com.neu.bsds.linyu.Server.Cache;

import com.neu.bsds.linyu.Server.DAO.ServerMetricDAO;
import com.neu.bsds.linyu.Server.DataType.ServerMetric;
import com.neu.bsds.linyu.Server.DataType.SingleRideData;
import com.neu.bsds.linyu.Server.DAO.SingleRideDAO;
import com.neu.bsds.linyu.Server.DAO.SummaryRideDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by linyuyu on 10/27/17.
 */
public class Cacher {

    private static final int POST_CYCLE = 3000;

    private static final int BATCH_SIZE = 5000;

    private static final int PUBLISH_CYCLE = 3000;

    private static final int PUBLISH_SIZE = 5000;

    private static long startTime = System.currentTimeMillis();

    private static long startTimePublish = System.currentTimeMillis();

    private static final ScheduledExecutorService postService = Executors.newScheduledThreadPool(1);

    private static final ScheduledExecutorService publishService = Executors.newScheduledThreadPool(1);

    public static void start() {
//        SQSConnection.init();
        postService.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        BatchPost();
                    }

                }, 0, POST_CYCLE, TimeUnit.MILLISECONDS);

        publishService.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        MessagePost();
                    }
                }, 0, PUBLISH_CYCLE, TimeUnit.MILLISECONDS);

    }

    private static void BatchPost() {
        long currentTime = System.currentTimeMillis();
        long timePeriod = currentTime - startTime;
        if (PostCache.getSize() > BATCH_SIZE || (timePeriod > POST_CYCLE && PostCache.getSize() != 0)) {
            List<SingleRideData> cachedPost = PostCache.getCacheList();
            PostCache.cleanCache();
            startTime = currentTime;
            try {
                SingleRideDAO.batchPost(cachedPost);
                SummaryRideDAO.batchUpdate(cachedPost);

//                List<String> cachedMessage = ServerMetricCache.getMessageCacheList();
////                startTimePublish = currentTime;
//                PublishToSQS(cachedMessage);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private static void MessagePost () {
        long currentTime = System.currentTimeMillis();
        long timePeriod = currentTime - startTimePublish;
        if (ServerMetricCache.getSize() > PUBLISH_SIZE || (timePeriod > PUBLISH_CYCLE && ServerMetricCache.getSize() != 0)) {
            List<ServerMetric> cachedMetric = ServerMetricCache.getMessageCacheList();
            startTimePublish = currentTime;
//            PublishToSQS(cachedMessage);
            try {
                ServerMetricDAO.batchPost(cachedMetric);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    private static void PublishToSQS (List<String> messageList) {
//        if (messageList.size() != 0) {
//            SQSConnection.batchPublish(messageList);
//        }
//    }
}
