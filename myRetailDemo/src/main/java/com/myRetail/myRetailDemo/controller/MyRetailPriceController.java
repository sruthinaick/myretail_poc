package com.myRetail.myRetailDemo.controller;


import com.myRetail.myRetailDemo.entity.Price;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.service.price.PriceCreateService;
import com.myRetail.myRetailDemo.service.price.PriceGetService;
import com.myRetail.myRetailDemo.service.price.PriceUpdateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(MyRetailPriceController.MY_PRICE_PATH)
@Api(tags = {MyRetailPriceController.MY_PRICE_TAG})
public class MyRetailPriceController {

    public static final String MY_PRICE_PATH = "/myretail";
    public static final String MY_PRICE_TAG = "API to fetch,save and update price details in myRetail";
    private static final Logger logger = LoggerFactory.getLogger(MyRetailPriceController.class);

    @Autowired
    private PriceGetService priceGetService;

    @Autowired
    private PriceCreateService priceCreateService;

    @Autowired
    private PriceUpdateService priceUpdateService;


    @GetMapping(value="/products/{productId}/price",produces = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="Get the product price details based on product id",
            notes="Returns the meta information about the product price based on product id",
            httpMethod = "GET")
    public ResponseEntity getPrice(@PathVariable Long productId){
        Optional price = null;
        try {
            logger.info("Before fetching product price by productId");
            price = priceGetService.getPriceById(productId);
            logger.info("After fetching product price by productId", price);
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.errorResponse);
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        return ResponseEntity.ok(price);
    }

    @PostMapping(value="/products/price",consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="Save the product price",
            notes="Saves the meta information about the product",
            httpMethod = "POST")
    public ResponseEntity savePrice(@RequestBody Price price) {
        Price responsePrice = null;
        try {
            logger.info("Before saving price");
            responsePrice = priceCreateService.createPrice(price);
            logger.info("After saving price");
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.errorResponse);
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        return ResponseEntity.ok(null);
    }


    @PutMapping(value="/products/{productId}/price",consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="Update the product price",
            notes="Update the meta information about the product",
            httpMethod = "PUT")
    public ResponseEntity updatePrice(@PathVariable Long productId, @RequestBody Price price) {
        Price responsePrice = null;
        try {
            logger.info("Before updating product price");
            responsePrice = priceUpdateService.updatePrice(productId, price);
            logger.info("After updating product price");
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.errorResponse);
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        return ResponseEntity.ok(null);
    }


}