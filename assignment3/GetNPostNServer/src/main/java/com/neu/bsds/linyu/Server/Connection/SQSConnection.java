package com.neu.bsds.linyu.Server.Connection;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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
 * Created by linyuyu on 11/20/17.
 */
public class SQSConnection {
    private static AmazonSQS sqs;
    private static String myQueueUrl;
    private static String QUEUE_NAME = "MyQueue";

    public static void init () {
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_WEST_2)
                .build();

        // Create a queue
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(QUEUE_NAME);
        myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
    }

    public static void batchPublish (List<String> messageList) {
        try {
            List<SendMessageBatchRequestEntry> messages = new ArrayList<>();
            for (int i = 0; i < messageList.size(); i++) {
                messages.add(new SendMessageBatchRequestEntry(Integer.toString(i), messageList.get(i)));
            }
            sqs.sendMessageBatch(new SendMessageBatchRequest(myQueueUrl, messages));
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it " +
                    "to Amazon SQS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered " +
                    "a serious internal problem while trying to communicate with SQS, such as not " +
                    "being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

    }


    public static void main (String[] args) {
        init();
        List<String> test = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            test.add("post" + "," + Integer.toString(i) + "," + "1");
        }
        for (int i = 0; i < 3; i++) {
            test.add("get" + "," + Integer.toString(i) + "," + "1");
        }

        batchPublish(test);
//        try {
//            // Create a queue
//            System.out.println("Creating a new SQS queue called MyQueue.\n");
//            CreateQueueRequest createQueueRequest = new CreateQueueRequest("MyQueue");
//            String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
//
//            // List queues
//            System.out.println("Listing all queues in your account.\n");
//            for (String queueUrl : sqs.listQueues().getQueueUrls()) {
//                System.out.println("  QueueUrl: " + queueUrl);
//            }
//            System.out.println();
//
//            // Send a message
//            System.out.println("Sending a message to MyQueue.\n");
//            sqs.sendMessage(new SendMessageRequest(myQueueUrl, "This is my message text."));
//
//            // Receive messages
//            System.out.println("Receiving messages from MyQueue.\n");
//            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
//            List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
//            for (Message message : messages) {
//                System.out.println("  Message");
//                System.out.println("    MessageId:     " + message.getMessageId());
//                System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
//                System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
//                System.out.println("    Body:          " + message.getBody());
//                for (Entry<String, String> entry : message.getAttributes().entrySet()) {
//                    System.out.println("  Attribute");
//                    System.out.println("    Name:  " + entry.getKey());
//                    System.out.println("    Value: " + entry.getValue());
//                }
//            }
//            System.out.println();
//
//            // Delete a message
//            System.out.println("Deleting a message.\n");
//            String messageReceiptHandle = messages.get(0).getReceiptHandle();
//            sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageReceiptHandle));
//
//            // Delete a queue
//            System.out.println("Deleting the test queue.\n");
//            sqs.deleteQueue(new DeleteQueueRequest(myQueueUrl));
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
    }
}
