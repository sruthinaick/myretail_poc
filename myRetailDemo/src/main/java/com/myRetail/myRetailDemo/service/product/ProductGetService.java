package com.myRetail.myRetailDemo.service.product;

import com.myRetail.myRetailDemo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductGetService {
   List<Product> getAllproducts();
   Optional<Product> getProductById(Long id);
}
