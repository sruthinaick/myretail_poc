package com.myRetail.myRetailDemo.service.productName;

import com.myRetail.myRetailDemo.entity.ProductName;

import java.util.Optional;

public interface ProductNameGetService {

    Optional<ProductName> getProductNameById(Long productId);
}
