package com.bakir.order_service.controller;

import com.bakir.order_service.dto.OrderRequest;
import com.bakir.order_service.dto.OrderResponse;
import com.bakir.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
 private final OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  String placeOrder(@RequestBody  OrderRequest orderRequest){
     orderService.placeOrder(orderRequest);
        return "place order successfully";
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(){

        return  ResponseEntity.ok(orderService.getAllOrders());
    }


}
