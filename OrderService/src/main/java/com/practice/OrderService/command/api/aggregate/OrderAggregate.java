package com.practice.OrderService.command.api.aggregate;

import com.practice.CommonService.commands.CompleteOrderCommand;
import com.practice.CommonService.events.OrderCompletedEvent;
import com.practice.OrderService.command.api.command.CreateOrderCommand;
import com.practice.OrderService.command.api.events.OrderCreatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private String orderStatus;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        //Validate the command
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(command, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event){
        this.orderId = event.getOrderId();
        this.productId = event.getProductId();
        this.userId = event.getUserId();
        this.addressId = event.getAddressId();
        this.quantity = event.getQuantity();
        this.orderStatus = event.getOrderStatus();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand command){
        //Validate the command
        //Publish Order completed event
        OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent
                .builder()
                .orderStatus(command.getOrderStatus())
                .orderId(command.getOrderId())
                .build();

        AggregateLifecycle.apply(orderCompletedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event){
        this.orderStatus = event.getOrderStatus();
    }
}
