package com.dailycodebuffer.product.model;

import lombok.Data;

/**
 * Product request body
 *
 * @author Glenn Cai
 * @version 1.0 14/08/2023
 */
@Data
public class ProductRequest {

  /**
   * Product name
   */
  private String name;

  /**
   * Product price
   */
  private long price;

  /**
   * Product quantity
   */
  private long quantity;
}
