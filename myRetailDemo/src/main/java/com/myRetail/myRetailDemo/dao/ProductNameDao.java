package com.myRetail.myRetailDemo.dao;

import com.myRetail.myRetailDemo.entity.ProductName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductNameDao extends JpaRepository<ProductName, Long> {

}


