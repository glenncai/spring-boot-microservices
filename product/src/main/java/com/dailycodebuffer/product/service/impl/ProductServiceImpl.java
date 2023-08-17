package com.dailycodebuffer.product.service.impl;

import com.dailycodebuffer.product.entity.Product;
import com.dailycodebuffer.product.exception.ProductServiceCustomException;
import com.dailycodebuffer.product.model.ProductRequest;
import com.dailycodebuffer.product.model.ProductResponse;
import com.dailycodebuffer.product.repository.ProductRepository;
import com.dailycodebuffer.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Product service implementation class
 *
 * @author Glenn Cai
 * @version 1.0 14/08/2023
 */
@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

  @Resource
  private ProductRepository productRepository;

  /**
   * Add product
   *
   * @param productRequest product request body
   * @return product id
   */
  @Override
  public long addProduct(ProductRequest productRequest) {
    Product product = new Product();
    product.setProductName(productRequest.getName());
    product.setPrice(productRequest.getPrice());
    product.setQuantity(productRequest.getQuantity());

    Product savedProduct = productRepository.save(product);
    return savedProduct.getProductId();
  }

  /**
   * Get product by id
   *
   * @param id product id
   * @return product response
   */
  @Override
  public ProductResponse getProductById(long id) {
    Product product = productRepository.findById(id).orElseThrow(
        () -> new ProductServiceCustomException("Product not found", "PRODUCT_NOT_FOUND"));

    ProductResponse productResponse = new ProductResponse();
    BeanUtils.copyProperties(product, productResponse);
    return productResponse;
  }

  /**
   * Reduce product quantity
   *
   * @param id       product id
   * @param quantity quantity to reduce
   */
  @Override
  public void reduceQuantity(long id, long quantity) {
    log.info("Reduce product quantity by id: {}, quantity: {}", id, quantity);

    Product product = productRepository.findById(id).orElseThrow(
        () -> new ProductServiceCustomException("Product not found", "PRODUCT_NOT_FOUND"));

    long currentQuantity = product.getQuantity();
    if (currentQuantity < quantity) {
      throw new ProductServiceCustomException("Product quantity is not enough",
                                              "INSUFFICIENT_PRODUCT_QUANTITY");
    }

    product.setQuantity(currentQuantity - quantity);
    productRepository.save(product);
    log.info("Reduce product quantity successfully");
  }
}
