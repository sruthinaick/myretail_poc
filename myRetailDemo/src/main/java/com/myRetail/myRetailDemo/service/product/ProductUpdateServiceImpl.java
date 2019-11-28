package com.myRetail.myRetailDemo.service.product;

import com.myRetail.myRetailDemo.dao.ProductDao;
import com.myRetail.myRetailDemo.entity.Price;
import com.myRetail.myRetailDemo.entity.Product;
import com.myRetail.myRetailDemo.entity.ProductName;
import com.myRetail.myRetailDemo.exceptions.ErrorCode;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductUpdateServiceImpl implements ProductUpdateService{

    static private final XLogger logger = XLoggerFactory.getXLogger(ProductUpdateServiceImpl.class);

    @Autowired
    private ProductDao productDao;

    private RestTemplate restTemplate = new RestTemplate();

    @Value("http://localhost:8080/myretail/products/{0}/price")
    private String pricePutServiceUrl;

    @Value("http://localhost:8080/myretail/products/{0}/productname")
    private String productNamePutServiceUrl;


    public void updateProduct(Long existingProductId, Product updatedProduct){
        //Product responseProduct = null;
        if (productDao.existsById(existingProductId)){
            logger.info("Going to update a product {}", existingProductId, updatedProduct);
            Optional<Product> products = null;
            try {
                updatePriceRestService(existingProductId, updatedProduct.getPrice());
                updateProductNameRestService(existingProductId, new ProductName(existingProductId, updatedProduct.getProductName()));
                //products = productDao.findById(existingProductId);
                //responseProduct = productDao.findById(existingProductId).get();
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

        //return responseProduct;
    }

    private void updateProductNameRestService(Long existingProductId, ProductName productName) throws RestClientException {
        //ProductName responseProductName = null;
        try {
            if(productName != null) {
                String url = MessageFormat.format(productNamePutServiceUrl, existingProductId);
                restTemplate.put(url, productName, existingProductId);
            }
        }catch (ResourceNotFoundException e){
            logger.error("Error",e);
            throw new ResourceNotFoundException(null);
        }catch (RestClientException e){
            logger.error("Throwing Rest Client exception",e);
            throw new ServiceException(ErrorCode.SERVICE_UNAVAILABLE.getCode(),ErrorCode.SERVICE_UNAVAILABLE.getDescription());
        }
        //return responseProductName;
    }

    private void updatePriceRestService(Long existingProductId, Price price) throws RestClientException {
        //Price responsePrice = null;
        try {
            if(price != null) {
                String url = MessageFormat.format(pricePutServiceUrl, existingProductId);
                restTemplate.put (url, price, existingProductId);
            }
        }catch (ResourceNotFoundException e){
            logger.error("Error",e);
            throw new ResourceNotFoundException(null);
        }catch (RestClientException e){
            logger.error("Throwing Rest Client exception",e);
            throw new ServiceException(ErrorCode.SERVICE_UNAVAILABLE.getCode(),ErrorCode.SERVICE_UNAVAILABLE.getDescription());
        }
        //return responsePrice;
    }

}


