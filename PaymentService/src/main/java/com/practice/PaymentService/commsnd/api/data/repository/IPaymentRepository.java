package com.practice.PaymentService.commsnd.api.data.repository;

import com.practice.PaymentService.commsnd.api.data.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, String> {
}
