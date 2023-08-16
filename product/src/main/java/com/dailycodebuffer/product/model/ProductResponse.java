package com.dailycodebuffer.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product response dto
 *
 * @author Glenn Cai
 * @version 1.0 15/08/2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

  /**
   * Product id
   */
  private long productId;

  /**
   * Product name
   */
  private String productName;

  /**
   * Product price
   */
  private long price;

  /**
   * Product quantity
   */
  private long quantity;
}
