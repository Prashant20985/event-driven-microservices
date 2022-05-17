package com.practice.ShipmentService.command.api.events;

import com.practice.CommonService.events.OrderShippedEvent;
import com.practice.ShipmentService.command.api.data.entity.Shipment;
import com.practice.ShipmentService.command.api.data.repository.IShipmentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ShipmentEventHandler {

    private IShipmentRepository repository;

    public ShipmentEventHandler(IShipmentRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(OrderShippedEvent event){
        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(event,shipment);
        repository.save(shipment);
    }
}
