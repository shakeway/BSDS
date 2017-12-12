package com.neu.bsds.linyu;

import com.amazonaws.services.sqs.model.Message;
import com.neu.bsds.linyu.Cache.ServerMetricCache;
import com.neu.bsds.linyu.DAO.ServerMetricDAO;
import com.neu.bsds.linyu.DataType.ServerMetric;
import com.neu.bsds.linyu.SQSConnection;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by linyuyu on 11/20/17.
 */
public class StartSQSClient {
    private static int PULL_CYCLE = 3000;
    private static int BATCH_SIZE = 5000;
    private static String POST_IDENTIFIER = "post";
    private static String GET_IDENTIFIER = "get";
    private static long startTime = System.currentTimeMillis();


    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public static void main (String[] args) {
        SQSConnection.init();
        service.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        MessagePull();
                        BatchPost();
                    }

                }, 0, PULL_CYCLE, TimeUnit.MILLISECONDS);
    }


    private static void MessagePull () {
        long currentTime = System.currentTimeMillis();
        long timePeriod = currentTime - startTime;
        if (timePeriod > PULL_CYCLE) {
            List<Message> messages = SQSConnection.pullMessage();
            if (messages.isEmpty()) {
                return;
            }

            for (Message message: messages) {
                cacheMessage(message.getBody());
            }
            SQSConnection.batchDelete(messages);

            startTime = currentTime;

        }
    }

    private static void cacheMessage (String message) {
        String[] splitedMessage = message.split(",");
        if (splitedMessage[0].equals(POST_IDENTIFIER)) {
            ServerMetricCache.add(Integer.parseInt(splitedMessage[1]), Integer.parseInt(splitedMessage[2]), 0);
        } else if (splitedMessage[0].equals(GET_IDENTIFIER)) {
            ServerMetricCache.add(Integer.parseInt(splitedMessage[1]), Integer.parseInt(splitedMessage[2]), 1);
        } else {
            System.out.println("no effective message");
            return;
        }
    }

    private static void BatchPost() {
        int s = ServerMetricCache.getSize();

        long currentTime = System.currentTimeMillis();
        long timePeriod = currentTime - startTime;
        if (ServerMetricCache.getSize() > BATCH_SIZE || (timePeriod > PULL_CYCLE && ServerMetricCache.getSize() > 0)) {
            List<ServerMetric> cache = ServerMetricCache.getMetriCacheList();
            ServerMetricCache.cleanMetricCacheList();
            try {
                ServerMetricDAO.batchPost(cache);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
