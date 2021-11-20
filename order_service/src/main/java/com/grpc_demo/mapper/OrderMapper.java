package com.grpc_demo.mapper;

import com.grpc_demo.common_schemas.CreateOrderRequest;
import com.grpc_demo.common_schemas.OrderResponse;
import com.grpc_demo.model.Order;
import com.grpc_demo.model.Product;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class OrderMapper {

    public Order convert(CreateOrderRequest request) {
        Order order = new Order();
        order.setOrderStatus("ORDER_CREATED");
        order.setCustomerId(request.getCustomerId());
        order.setSellerId(request.getSellerId());
        Product product = new Product();
        product.setProductId(request.getProduct().getProductId());
        product.setProductName(request.getProduct().getProductName());
        order.setProduct(product);
        order.setDeliveryLocation(request.getDeliveryLocation());
        order.setPaymentMethod(request.getPaymentMethod().toString());
        String time = new Timestamp(System.currentTimeMillis()).toString();
        order.setCreatedTimestamp(time);
        order.setUpdatedTimestamp(time);
        return order;
    }

    public OrderResponse convert(Order order) {
        com.grpc_demo.common_schemas.Product item = com.grpc_demo.common_schemas.Product.newBuilder()
                .setProductId(order.getProduct().getProductId())
                .setProductName(order.getProduct().getProductName())
                .build();
        OrderResponse orderResponse = OrderResponse.newBuilder()
                .setCustomerId(order.getCustomerId())
                .setDeliveryLocation(order.getDeliveryLocation())
                .setPaymentMethod(order.getPaymentMethod())
                .setOrderStatus(order.getOrderStatus())
                .setSellerId(order.getSellerId())
                .setOrderId(order.getOrderId())
                .setProduct(item)
                .setCreatedTime(order.getCreatedTimestamp())
                .setUpdatedTime(order.getUpdatedTimestamp())
                .build();
        return orderResponse;
    }
}
