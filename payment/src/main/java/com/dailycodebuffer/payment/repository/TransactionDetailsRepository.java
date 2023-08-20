package com.dailycodebuffer.payment.repository;

import com.dailycodebuffer.payment.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Transaction details repository.
 *
 * @author Glenn Cai
 * @version 1.0 20/08/2023
 */
@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
  TransactionDetails findByOrderId(long orderId);
}
