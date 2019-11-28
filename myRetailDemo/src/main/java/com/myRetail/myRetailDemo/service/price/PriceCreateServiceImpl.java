package com.myRetail.myRetailDemo.service.price;

import com.myRetail.myRetailDemo.entity.Price;
import com.myRetail.myRetailDemo.exceptions.ErrorCode;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.dao.PriceDao;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

@Service
public class PriceCreateServiceImpl implements PriceCreateService{
    @Autowired
    private PriceDao priceDAO;

    static private final XLogger logger = XLoggerFactory.getXLogger(PriceCreateServiceImpl.class);

    public Price createPrice(Price price){
        Price responsePrice = null;
        try {
            responsePrice = priceDAO.save(price);
        } catch (DataAccessResourceFailureException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        return responsePrice;
    }

}