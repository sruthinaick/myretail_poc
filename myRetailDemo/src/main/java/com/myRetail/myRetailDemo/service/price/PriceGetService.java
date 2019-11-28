package com.myRetail.myRetailDemo.service.price;

import com.myRetail.myRetailDemo.entity.Price;

import java.util.Optional;

public interface PriceGetService {
    Optional<Price> getPriceById(Long productId);
}


