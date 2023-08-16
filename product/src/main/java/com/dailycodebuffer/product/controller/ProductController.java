package com.dailycodebuffer.product.controller;

import com.dailycodebuffer.product.model.ProductRequest;
import com.dailycodebuffer.product.model.ProductResponse;
import com.dailycodebuffer.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Product controller class
 *
 * @author Glenn Cai
 * @version 1.0 14/08/2023
 */
@RestController
@RequestMapping("/product")
public class ProductController {

  @Resource
  private ProductService productService;

  /**
   * Add product
   *
   * @param productRequest product request body
   * @return product id
   */
  @PostMapping
  public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {
    long productId = productService.addProduct(productRequest);
    return new ResponseEntity<>(productId, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long id) {
    ProductResponse productResponse = productService.getProductById(id);
    return new ResponseEntity<>(productResponse, HttpStatus.OK);
  }
}
