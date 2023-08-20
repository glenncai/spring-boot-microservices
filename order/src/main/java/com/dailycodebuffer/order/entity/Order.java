package com.dailycodebuffer.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import javax.persistence.*;

/**
 * Order entity
 *
 * @author Glenn Cai
 * @version 1.0 16/08/2023
 */
@Entity
@Table(name = "order_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

  /**
   * Order id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

  /**
   * Product id
   */
  @Column(name = "product_id")
  private long productId;

  /**
   * Order quantity
   */
  @Column(name = "quantity")
  private long quantity;

  /**
   * Order date
   */
  @Column(name = "order_date")
  private Instant orderDate;

  /**
   * Order status
   */
  @Column(name = "order_status")
  private String orderStatus;

  /**
   * Order amount
   */
  @Column(name = "total_amount")
  private long amount;
}
