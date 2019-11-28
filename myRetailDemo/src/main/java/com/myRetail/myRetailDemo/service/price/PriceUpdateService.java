package com.myRetail.myRetailDemo.service.price;

import com.myRetail.myRetailDemo.entity.Price;

public interface PriceUpdateService {
    Price updatePrice(Long existingProductId, Price Price);
}
