package com.practice.OrderService.command.api.data.repository;

import com.practice.OrderService.command.api.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, String> {
}
