package com.practice.OrderService.command.api.saga;

import com.practice.CommonService.commands.CompleteOrderCommand;
import com.practice.CommonService.commands.ShipOrderCommand;
import com.practice.CommonService.commands.ValidatePaymentCommand;
import com.practice.CommonService.events.OrderCompletedEvent;
import com.practice.CommonService.events.OrderShippedEvent;
import com.practice.CommonService.events.PaymentProcessedEvent;
import com.practice.CommonService.models.User;
import com.practice.CommonService.queries.GetUserPaymentDetailsQuery;
import com.practice.OrderService.command.api.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    public OrderProcessingSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderCreatedEvent event){
        log.info("OrderCreatedEvent in saga for Order Id : {}",
                event.getOrderId());
        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery =
                new GetUserPaymentDetailsQuery(event.getUserId());

        User user = null;

        try {
            user = queryGateway.query(getUserPaymentDetailsQuery,
                    ResponseTypes.instanceOf(User.class)).join();
        }catch (Exception e){
            log.error(e.getMessage());
            //Start the Compensating Transaction
        }

        ValidatePaymentCommand validatePaymentCommand =
                 ValidatePaymentCommand.builder()
                         .cardDetails(user.getCardDetails())
                         .orderId(event.getOrderId())
                         .paymentId(UUID.randomUUID().toString())
                .build();

        commandGateway.sendAndWait(validatePaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(PaymentProcessedEvent event){
        log.info("PaymentProcessedEvent in saga for Order Id : {}",
                event.getOrderId());

        try {
            ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                    .shipmentId(UUID.randomUUID().toString())
                    .orderId(event.getOrderId())
                    .build();

            commandGateway.send(shipOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            //Start the Compensation Transaction
        }

    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderShippedEvent event){

        log.info("OrderShipmentEvent in saga for Order Id : {}",
                event.getOrderId());

        CompleteOrderCommand command = CompleteOrderCommand.builder()
                .orderId(event.getOrderId())
                .orderStatus("APPROVED")
                .build();

        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCompletedEvent event){
        log.info("OrderCompletedEvent in saga for Order Id : {}",
                event.getOrderId());

    }
}
