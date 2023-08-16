package com.dailycodebuffer.product.repository;

import com.dailycodebuffer.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Product repository
 *
 * @author Glenn Cai
 * @version 1.0 14/08/2023
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
