package com.bakir.order_service.service;

import com.bakir.order_service.dto.OrderLineItemsDto;
import com.bakir.order_service.dto.OrderRequest;
import com.bakir.order_service.dto.OrderResponse;
import com.bakir.order_service.model.Order;
import com.bakir.order_service.model.OrderLineItems;
import com.bakir.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public void  placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        System.out.println(orderRequest.getOrderLineItemsDtoList());
        List<OrderLineItems>  orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapTODto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);
        orderRepository.save(order);
        log.info("Order placed successfully");

    }

    private OrderLineItems mapTODto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkucode(orderLineItemsDto.getSkucode());
        return orderLineItems;
    }


    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    List<OrderLineItemsDto> items = order.getOrderLineItemsList()
                            .stream()
                            .map(this::mapToOrderLineItemsDto)
                            .toList();

                    return OrderResponse.builder()
                            .orderId(order.getId())
                            .orderNumber(order.getOrderNumber())
                            .orderLineItemsDtoList(items)
                            .build();
                })
                .toList();
    }

    private OrderLineItemsDto mapToOrderLineItemsDto(OrderLineItems item) {
        return new OrderLineItemsDto(
                item.getId(),
                item.getSkucode(),
                item.getPrice(),
                item.getQuantity()
        );
    }

}
