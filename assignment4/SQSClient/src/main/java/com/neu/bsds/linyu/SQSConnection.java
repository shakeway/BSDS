package com.neu.bsds.linyu;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;

import java.util.ArrayList;
import java.util.List;

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

        //get queue url
        myQueueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
    }


    public static List<Message> pullMessage () {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
        receiveMessageRequest.setWaitTimeSeconds(10);
        return sqs.receiveMessage(receiveMessageRequest).getMessages();
    }


    public static void batchDelete (List<Message> messageList) {
        List<DeleteMessageBatchRequestEntry> messages = new ArrayList<>();
        for (Message message: messageList) {
            messages.add(new DeleteMessageBatchRequestEntry(message.getMessageId(), message.getReceiptHandle()));
        }
        sqs.deleteMessageBatch(myQueueUrl, messages);
    }
}
