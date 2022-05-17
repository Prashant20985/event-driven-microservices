package com.practice.PaymentService.commsnd.api.events;

import com.practice.CommonService.events.PaymentProcessedEvent;
import com.practice.PaymentService.commsnd.api.data.entity.Payment;
import com.practice.PaymentService.commsnd.api.data.repository.IPaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentsEventsHandler {

    private IPaymentRepository repository;

    public PaymentsEventsHandler(IPaymentRepository repository) {
        this.repository = repository;
    }


    @EventHandler
    public void on(PaymentProcessedEvent event){
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .orderStatus("COMPLETED")
                .timeStamp(new Date())
                .build();

        repository.save(payment);
    }
}
