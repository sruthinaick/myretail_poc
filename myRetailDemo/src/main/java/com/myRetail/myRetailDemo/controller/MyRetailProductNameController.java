package com.myRetail.myRetailDemo.controller;

import com.myRetail.myRetailDemo.entity.ProductName;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.service.productName.ProductNameCreateService;
import com.myRetail.myRetailDemo.service.productName.ProductNameGetService;
import com.myRetail.myRetailDemo.service.productName.ProductNameUpdateService;
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
@RequestMapping(MyRetailProductNameController.MY_PRODUCTNAME_PATH)
@Api(tags = {MyRetailProductNameController.MY_PRODUCTNAME_TAG})
public class MyRetailProductNameController {

    public static final String MY_PRODUCTNAME_PATH = "/myretail";
    public static final String MY_PRODUCTNAME_TAG = "API to fetch,save and update product name details in myRetail";
    private static final Logger logger = LoggerFactory.getLogger(MyRetailProductNameController.class);

    @Autowired
    private ProductNameGetService productNameGetService;

    @Autowired
    private ProductNameCreateService productNameCreateService;

    @Autowired
    private ProductNameUpdateService productNameUpdateService;


    @GetMapping(value="/products/{productId}/productname",produces = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="Get the product name details based on product id",
            notes="Returns the meta information about the product name based on product id",
            httpMethod = "GET")
    public ResponseEntity getProductName(@PathVariable Long productId){
        Optional productName = null;
        try {
            logger.info("Before fetching product name by productId");
            productName = productNameGetService.getProductNameById(productId);
            logger.info("After fetching product name by productId", productName);
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.errorResponse);
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        return ResponseEntity.ok(productName);
    }

    @PostMapping(value="/products/productname",consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="Save the product name",
            notes="Saves the meta information about the product",
            httpMethod = "POST")
    public ResponseEntity saveProductName(@RequestBody ProductName productName) {
        ProductName responseProductName = null;
        try {
            logger.info("Before saving productName");
            responseProductName = productNameCreateService.createProductName(productName);
            logger.info("After saving productName");
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.errorResponse);
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        return ResponseEntity.ok(null);
    }


    @PutMapping(value="/products/{productId}/productname",consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="Update the product name",
            notes="Update the meta information about the product",
            httpMethod = "PUT")
    public ResponseEntity updateProductName(@PathVariable Long productId, @RequestBody ProductName productName) {
        ProductName responseProductName = null;
        try {
            logger.info("Before updating product name");
            responseProductName = productNameUpdateService.updateProductName(productId, productName);
            logger.info("After updating product name");
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.errorResponse);
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        return ResponseEntity.ok(null);
    }


}