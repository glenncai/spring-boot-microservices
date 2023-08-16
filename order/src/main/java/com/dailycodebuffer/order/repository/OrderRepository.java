package com.dailycodebuffer.order.repository;

import com.dailycodebuffer.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Order repository
 *
 * @author Glenn Cai
 * @version 1.0 16/08/2023
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
