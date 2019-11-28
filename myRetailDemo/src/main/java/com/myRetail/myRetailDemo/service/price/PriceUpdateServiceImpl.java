package com.myRetail.myRetailDemo.service.price;


import com.myRetail.myRetailDemo.dao.PriceDao;
import com.myRetail.myRetailDemo.entity.Price;
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
public class PriceUpdateServiceImpl implements PriceUpdateService{
    @Autowired
    private PriceDao priceDao;

    static private final XLogger logger = XLoggerFactory.getXLogger(PriceUpdateServiceImpl.class);

    public Price updatePrice(Long existingProductId, Price updatedPrice) {
        Price responsePrice = null;
        if(priceDao.existsById(existingProductId)){
            logger.info("Going to update a price {}", existingProductId, updatedPrice);
            Optional<Price> priceList = null;
            try {
                priceList = priceDao.findById(existingProductId);
                Price existingPrice = priceList.get();
                existingPrice.setCurrency(updatedPrice.getCurrency() == null? existingPrice.getCurrency() : updatedPrice.getCurrency());
                existingPrice.setMaxRetailPrice(updatedPrice.getMaxRetailPrice() == null? existingPrice.getMaxRetailPrice() : updatedPrice.getMaxRetailPrice());
                existingPrice.setOfferPrice(updatedPrice.getOfferPrice() == null? existingPrice.getOfferPrice() : updatedPrice.getOfferPrice());
                responsePrice = priceDao.save(existingPrice);
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
        return responsePrice;
    }



}
