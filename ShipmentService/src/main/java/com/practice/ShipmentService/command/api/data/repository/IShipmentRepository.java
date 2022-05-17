package com.practice.ShipmentService.command.api.data.repository;

import com.practice.ShipmentService.command.api.data.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShipmentRepository extends JpaRepository<Shipment, String> {
}
