package com.practice.ShipmentService.command.api.aggregate;

import com.practice.CommonService.commands.ShipOrderCommand;
import com.practice.CommonService.events.OrderShippedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class ShipmentAggregate {
    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;

    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand command) {
        //Validate the command
        //Publish the OrderShipped Event
        OrderShippedEvent orderShippedEvent = OrderShippedEvent.builder()
                .orderId(command.getOrderId())
                .shipmentId(command.getShipmentId())
                .shipmentStatus("COMPLETED")
                .build();

        AggregateLifecycle.apply(orderShippedEvent);
    }

    @EventSourcingHandler
    private void on(OrderShippedEvent event){
        this.orderId = event.getOrderId();
        this.shipmentId = event.getShipmentId();
        this.shipmentStatus = event.getShipmentStatus();
    }
}
