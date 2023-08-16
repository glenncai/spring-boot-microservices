package com.dailycodebuffer.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Product entity class
 *
 * @author Glenn Cai
 * @version 1.0 14/08/2023
 */
@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "product_id")
  private long productId;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "price")
  private long price;

  @Column(name = "quantity")
  private long quantity;
}
