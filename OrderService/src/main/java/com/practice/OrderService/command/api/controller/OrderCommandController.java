package com.practice.OrderService.command.api.controller;

import com.practice.OrderService.command.api.command.CreateOrderCommand;
import com.practice.OrderService.command.api.models.OrderRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

    private CommandGateway commandGateway;

    public OrderCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderRestModel modal){

        String orderId = UUID.randomUUID().toString();

        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(orderId)
                .addressId(modal.getAddressId())
                .productId(modal.getProductId())
                .quantity(modal.getQuantity())
                .orderStatus("CREATED")
                .build();

        commandGateway.sendAndWait(createOrderCommand);
        return "Order Created";
    }
}
