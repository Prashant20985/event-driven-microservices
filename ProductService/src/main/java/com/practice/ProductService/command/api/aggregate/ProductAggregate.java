package com.practice.ProductService.command.api.aggregate;


import com.practice.ProductService.command.api.commands.CreateProductCommand;
import com.practice.ProductService.command.api.events.ProductCreateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private  Integer quantity;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        //Perform Validation
        ProductCreateEvent productCreateEvent = new ProductCreateEvent();
        BeanUtils.copyProperties(createProductCommand, productCreateEvent);

        //Publish Event
        AggregateLifecycle.apply(productCreateEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreateEvent productCreateEvent){
        this.name = productCreateEvent.getName();
        this.productId = productCreateEvent.getProductId();
        this.price = productCreateEvent.getPrice();
        this.quantity = productCreateEvent.getQuantity();
    }
}
