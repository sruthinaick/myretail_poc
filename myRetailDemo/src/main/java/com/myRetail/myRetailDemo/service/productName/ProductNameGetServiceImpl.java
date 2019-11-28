package com.myRetail.myRetailDemo.service.productName;

import com.myRetail.myRetailDemo.entity.ProductName;
import com.myRetail.myRetailDemo.exceptions.ErrorCode;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.dao.ProductNameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductNameGetServiceImpl implements ProductNameGetService {

    @Autowired
    private ProductNameDao productNameDao;

    public Optional<ProductName> getProductNameById(Long productId) {
        Optional<ProductName> productName = null;

        try {
            productName = productNameDao.findById(productId);
        } catch (DataAccessResourceFailureException e) {
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(productId.toString());
        }

        return productName;
    }

}