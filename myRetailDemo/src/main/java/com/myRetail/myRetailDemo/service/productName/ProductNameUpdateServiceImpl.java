package com.myRetail.myRetailDemo.service.productName;

import com.myRetail.myRetailDemo.dao.ProductNameDao;
import com.myRetail.myRetailDemo.entity.ProductName;
import com.myRetail.myRetailDemo.exceptions.ErrorCode;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ProductNameUpdateServiceImpl implements ProductNameUpdateService{

    @Autowired
    private ProductNameDao productNameDAO;

    static private final XLogger logger = XLoggerFactory.getXLogger(ProductNameUpdateServiceImpl.class);

    public ProductName updateProductName(Long existingProductId, ProductName updatedProductName){
        ProductName responseProductName = null;
        if(productNameDAO.existsById(existingProductId)){
            logger.info("Going to update a product name {}", existingProductId, updatedProductName);
            Optional<ProductName> productName = null;
            try {
                productName = productNameDAO.findById(existingProductId);
                ProductName existingProductName = productName.get();
                existingProductName.setProductName(updatedProductName.getProductName() == null? existingProductName.getProductName() : updatedProductName.getProductName() );
                responseProductName = productNameDAO.save(existingProductName);

            } catch (DataAccessResourceFailureException e) {
                throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
            }
            catch (NoSuchElementException e) {
                logger.error(e.getMessage(),e);
                throw new ResourceNotFoundException(existingProductId.toString());
            }
        }
        else {
            throw new ResourceNotFoundException(existingProductId.toString());
        }
        return responseProductName;
    }



}
