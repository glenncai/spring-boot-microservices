package com.dailycodebuffer.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import javax.persistence.*;

/**
 * Transaction details entity
 *
 * @author Glenn Cai
 * @version 1.0 20/08/2023
 */
@Entity
@Table(name = "transaction_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetails {

  /**
   * Transaction id
   */
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /**
   * Order id
   */
  @Column(name = "order_id")
  private long orderId;

  /**
   * Payment mode
   */
  @Column(name = "mode")
  private String paymentMode;

  /**
   * Reference number
   */
  @Column(name = "reference_number")
  private String referenceNumber;

  /**
   * Payment date
   */
  @Column(name = "payment_date")
  private Instant paymentDate;

  /**
   * Payment status
   */
  @Column(name = "status")
  private String paymentStatus;

  /**
   * Payment amount
   */
  @Column(name = "amount")
  private long amount;

}
