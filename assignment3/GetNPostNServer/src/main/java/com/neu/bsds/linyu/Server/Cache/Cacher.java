package com.neu.bsds.linyu.Server.Cache;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;
import com.neu.bsds.linyu.Server.Connection.SQSConnection;
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

    private static long startTime = System.currentTimeMillis();

    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public static void start() {
        SQSConnection.init();
        service.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        BatchPost();
                        MessagePublish();
                    }

                }, 0, POST_CYCLE, TimeUnit.MILLISECONDS);

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
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private static void MessagePublish () {
        long currentTime = System.currentTimeMillis();
        long timePeriod = currentTime - startTime;
        if (MessageCache.GetMessageCacheListSize() > BATCH_SIZE || (timePeriod > POST_CYCLE && MessageCache.GetMessageCacheListSize() != 0)) {
            List<String> cachedMessage = MessageCache.getGetMessageCacheList();
            MessageCache.cleanGetMessageCacheList();
            startTime = currentTime;
            PublishToSQS(cachedMessage);

        }

        if (MessageCache.PostMessageCacheListSize() > BATCH_SIZE || (timePeriod > POST_CYCLE && MessageCache.PostMessageCacheListSize() != 0)) {
            List<String> cachedMessage = MessageCache.getPostMessageCacheList();
            MessageCache.cleanGetMessageCacheList();
            startTime = currentTime;
            PublishToSQS(cachedMessage);

        }
    }

    private static void PublishToSQS (List<String> messageList) {
        if (messageList.size() != 0) {
            SQSConnection.batchPublish(messageList);
        }
    }

}
