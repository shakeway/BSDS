package com.neu.bsds.linyu.Server.Connection;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;

/**
 * Created by linyuyu on 11/21/17.
 */
public class SQSConnection {
//    private static AmazonSQS sqs;
//    private static String myQueueUrl;
//    private static String QUEUE_NAME = "MyQueue";
//
//    public static void init () {
//        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
//        try {
//            credentialsProvider.getCredentials();
//        } catch (Exception e) {
//            throw new AmazonClientException(
//                    "Cannot load the credentials from the credential profiles file. " +
//                            "Please make sure that your credentials file is at the correct " +
//                            "location (~/.aws/credentials), and is in valid format.",
//                    e);
//        }
//
//        sqs = AmazonSQSClientBuilder.standard()
//                .withCredentials(credentialsProvider)
//                .withRegion(Regions.US_WEST_2)
//                .build();
//
//        // Create a queue
//        CreateQueueRequest createQueueRequest = new CreateQueueRequest(QUEUE_NAME);
//        myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
//    }

//    public static void batchPublish (List<String> messageList) {
//        try {
//            List<SendMessageBatchRequestEntry> messages = new ArrayList<>();
//            for (int i = 0; i < messageList.size(); i++) {
//                messages.add(new SendMessageBatchRequestEntry(Integer.toString(i), messageList.get(i)));
//            }
//            sqs.sendMessageBatch(new SendMessageBatchRequest(myQueueUrl, messages));
//        } catch (AmazonServiceException ase) {
//            System.out.println("Caught an AmazonServiceException, which means your request made it " +
//                    "to Amazon SQS, but was rejected with an error response for some reason.");
//            System.out.println("Error Message:    " + ase.getMessage());
//            System.out.println("HTTP Status Code: " + ase.getStatusCode());
//            System.out.println("AWS Error Code:   " + ase.getErrorCode());
//            System.out.println("Error Type:       " + ase.getErrorType());
//            System.out.println("Request ID:       " + ase.getRequestId());
//        } catch (AmazonClientException ace) {
//            System.out.println("Caught an AmazonClientException, which means the client encountered " +
//                    "a serious internal problem while trying to communicate with SQS, such as not " +
//                    "being able to access the network.");
//            System.out.println("Error Message: " + ace.getMessage());
//        }
//
//    }
}
