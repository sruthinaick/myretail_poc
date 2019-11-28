package com.myRetail.myRetailDemo.dao;

import com.myRetail.myRetailDemo.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceDao extends JpaRepository<Price, Long> {

}