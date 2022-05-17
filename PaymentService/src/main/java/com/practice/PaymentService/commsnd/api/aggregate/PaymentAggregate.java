package com.practice.PaymentService.commsnd.api.aggregate;

import com.practice.CommonService.commands.ValidatePaymentCommand;
import com.practice.CommonService.events.PaymentProcessedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
@Slf4j
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand command) {
        // Validate the Payment details
        // Publish the payment processed event
        log.info("Executing Validate Payment Command For Order Id: {} and Payment Id: {}",
                command.getOrderId(),command.getPaymentId());

        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(command.getPaymentId(),
                command.getOrderId());

        AggregateLifecycle.apply(paymentProcessedEvent);
        log.info("PaymentProcessedEvent Applied...");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent){
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getPaymentId();
    }
}

