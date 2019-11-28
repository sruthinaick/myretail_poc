package com.myRetail.myRetailDemo.service.productName;

import com.myRetail.myRetailDemo.entity.ProductName;
import com.myRetail.myRetailDemo.exceptions.ErrorCode;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.dao.ProductNameDao;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

@Service
public class ProductNameCreateServiceImpl implements ProductNameCreateService {
    @Autowired
    private ProductNameDao productNameDAO;

    static private final XLogger logger = XLoggerFactory.getXLogger(ProductNameCreateServiceImpl.class);

    public ProductName createProductName(ProductName productName){
        ProductName responseProductName = null;
        try {
            responseProductName = productNameDAO.save(productName);
        } catch (DataAccessResourceFailureException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        return responseProductName;
    }

}