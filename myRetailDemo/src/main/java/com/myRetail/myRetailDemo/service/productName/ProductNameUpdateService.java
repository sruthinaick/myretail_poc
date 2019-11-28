package com.myRetail.myRetailDemo.service.productName;

import com.myRetail.myRetailDemo.entity.ProductName;

public interface ProductNameUpdateService {
    ProductName updateProductName(Long existingProductId, ProductName productName);
}
