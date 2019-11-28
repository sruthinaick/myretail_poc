package com.myRetail.myRetailDemo.service.product;

import com.myRetail.myRetailDemo.entity.Product;
import org.springframework.stereotype.Service;

public interface ProductUpdateService {
    void updateProduct(Long existingProductId, Product product);
}
