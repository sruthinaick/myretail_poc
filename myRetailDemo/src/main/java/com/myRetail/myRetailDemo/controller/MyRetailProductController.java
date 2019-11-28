package com.myRetail.myRetailDemo.controller;

import com.myRetail.myRetailDemo.entity.Product;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.service.product.ProductCreateService;
import com.myRetail.myRetailDemo.service.product.ProductGetService;
import com.myRetail.myRetailDemo.service.product.ProductUpdateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(MyRetailProductController.MY_RETAIL_PATH)
@Api(tags = {MyRetailProductController.MY_RETAIL_TAG})
public class MyRetailProductController {

    public static final String MY_RETAIL_PATH = "/myretail";
    public static final String MY_RETAIL_TAG = "API to fetch,save and update product details in myRetail";
    private static final Logger logger = LoggerFactory.getLogger(MyRetailProductController.class);

    @Autowired
    private ProductGetService productGetService;

    @Autowired
    private ProductCreateService productCreateService;

    @Autowired
    private ProductUpdateService productUpdateService;


    @GetMapping(value="/allproducts")
    @ApiOperation(value="Get all the product details",
                  notes="Returns the meta information about all the products",
                  httpMethod = "GET")
    public List<Product> getAllProducts(){
        logger.info("Before fetching all products");
        List<Product> products = productGetService.getAllproducts();
        logger.info("After fetching all products", products);
        return products;
    }

     @GetMapping(value="/products/{productId}",produces = {MediaType.APPLICATION_JSON_VALUE} )
     @ApiOperation(value="Get the product details based on product id",
             notes="Returns the meta information about the product based on product id",
             httpMethod = "GET")
     public ResponseEntity getProduct(@PathVariable Long productId){
        Optional responseProduct = null;
        try {
            logger.info("Before fetching product by productId");
            responseProduct = productGetService.getProductById(productId);
            logger.info("After fetching product by productId", responseProduct);
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.errorResponse);
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        return ResponseEntity.ok(responseProduct);
    }

    @PostMapping(value="/products/",consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="Save the product details",
            notes="Saves the meta information about the product",
            httpMethod = "POST")
    public ResponseEntity saveProduct(@RequestBody Product product) {
        Product responseProduct = null;
        try {
            logger.info("Before saving product");
            responseProduct = productCreateService.createProduct(product);
            logger.info("After saving product");
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.errorResponse);
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        return ResponseEntity.ok(null);
    }

    @PutMapping(value="/products/{productId}",consumes = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="Update the product details",
            notes="Update the meta information about the product",
            httpMethod = "PUT")
    public ResponseEntity updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        Optional responseProduct = null;
        try {
            logger.info("Before updating product");
            productUpdateService.updateProduct(productId, product);
            responseProduct = productGetService.getProductById(productId);
            logger.info("After updating product");
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.errorResponse);
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.errorResponse);
        }
        return ResponseEntity.ok(null);
    }

}
