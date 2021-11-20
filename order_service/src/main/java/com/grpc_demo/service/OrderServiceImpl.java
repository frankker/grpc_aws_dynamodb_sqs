package com.grpc_demo.service;

import com.grpc_demo.common_schemas.CreateOrderRequest;
import com.grpc_demo.common_schemas.GetOrderRequest;
import com.grpc_demo.common_schemas.OrderResponse;
import com.grpc_demo.common_schemas.OrderServiceGrpc;
import com.grpc_demo.mapper.OrderMapper;
import com.grpc_demo.model.Order;
import com.grpc_demo.publisher.OrderPublisher;
import com.grpc_demo.repository.OrderRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    OrderPublisher orderPublisher;

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<OrderResponse> responseObserver) {

        Order order = orderMapper.convert(request);
        orderRepository.save(order);
        orderPublisher.publish(order);

        OrderResponse orderResponse = orderMapper.convert(order);

        responseObserver.onNext(orderResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getOrder(GetOrderRequest request, StreamObserver<OrderResponse> responseObserver) {

        Order orderById = orderRepository.getOrderById(request.getOrderId());

        OrderResponse orderResponse = orderMapper.convert(orderById);

        responseObserver.onNext(orderResponse);
        responseObserver.onCompleted();

    }
}
