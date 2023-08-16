package com.dailycodebuffer.product.service;

import com.dailycodebuffer.product.model.ProductRequest;
import com.dailycodebuffer.product.model.ProductResponse;

/**
 * Product service interface
 *
 * @author Glenn Cai
 * @version 1.0 14/08/2023
 */
public interface ProductService {
  /**
   * Add product
   *
   * @param productRequest product request body
   * @return product id
   */
  long addProduct(ProductRequest productRequest);

  /**
   * Get product by id
   *
   * @param id product id
   * @return product response
   */
  ProductResponse getProductById(long id);
}
