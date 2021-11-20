package com.grpc_demo.publisher;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.grpc_demo.model.Order;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderPublisher {

    private QueueMessagingTemplate queueMessagingTemplate;

    public OrderPublisher(AmazonSQSAsync amazonSQSAsync) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    }

    public void publish(Order order) {
        System.out.println("Publishing orderId: " + order.getOrderId());
        queueMessagingTemplate.convertAndSend("payments", order);
    }
}
