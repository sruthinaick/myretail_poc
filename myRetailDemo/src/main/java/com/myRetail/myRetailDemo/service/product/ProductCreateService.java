package com.myRetail.myRetailDemo.service.product;

import com.myRetail.myRetailDemo.entity.Product;

import java.util.List;

public interface ProductCreateService {
    Product createProduct(Product product);
    void createProducts(List<Product> products);
}
