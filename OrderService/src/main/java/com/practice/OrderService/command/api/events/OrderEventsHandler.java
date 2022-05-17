package com.practice.OrderService.command.api.events;

import com.practice.CommonService.events.OrderCompletedEvent;
import com.practice.OrderService.command.api.data.entity.Order;
import com.practice.OrderService.command.api.data.repository.IOrderRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
public class OrderEventsHandler {

    private IOrderRepository orderRepository;

    public OrderEventsHandler(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event){
        Order order = new Order();
        BeanUtils.copyProperties(event,order);
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event){
        Order order = orderRepository.findById(event.getOrderId()).get();

        order.setOrderStatus(event.getOrderStatus());
        orderRepository.save(order);
    }
}
