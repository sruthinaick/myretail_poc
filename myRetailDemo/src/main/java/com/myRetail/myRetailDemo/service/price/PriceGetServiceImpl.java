package com.myRetail.myRetailDemo.service.price;

import com.myRetail.myRetailDemo.entity.Price;
import com.myRetail.myRetailDemo.exceptions.ErrorCode;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.dao.PriceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PriceGetServiceImpl implements PriceGetService {

    @Autowired
    private PriceDao priceDao;

    public Optional<Price> getPriceById(Long productId) {
        Optional<Price> price = null;
        try {
            price = priceDao.findById(productId);
        } catch (DataAccessResourceFailureException e) {
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(productId.toString());
        }

        return price;
    }

}
