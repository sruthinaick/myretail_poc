package com.myRetail.myRetailDemo.service.product;

import com.myRetail.myRetailDemo.dao.ProductDao;
import com.myRetail.myRetailDemo.entity.Price;
import com.myRetail.myRetailDemo.entity.Product;
import com.myRetail.myRetailDemo.entity.ProductName;
import com.myRetail.myRetailDemo.exceptions.ErrorCode;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.dao.ProductDao;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductGetServiceImpl implements ProductGetService {

    static private final XLogger logger = XLoggerFactory.getXLogger(ProductGetServiceImpl.class);

    @Autowired
    private ProductDao productDao;

    private RestTemplate restTemplate = new RestTemplate();

    @Value("http://localhost:8080/myretail/products/{0}/price")
    private String priceServiceUrl;

    @Value("http://localhost:8080/myretail/products/{0}/productname")
    private String productNameServiceUrl;

    public List<Product> getAllproducts(){
        logger.info("Going to get all products");
        List<Product> products = null;
        try {
            products = productDao.findAll();
            for (Product product: products) {
                product.setProductName(getProductNameFromRestService(product.getProductId()).getProductName());
                product.setPrice(getPriceFromRestService(product.getProductId()));
            }
        } catch (DataAccessResourceFailureException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(), ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(), ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }

        logger.info("Successfully retrieved all products");
        return products;
    }

    public Optional<Product> getProductById(Long productId) {
        logger.info("Going to get the products based on productId", productId);
        Optional<Product> products = null;

        try {
            products = productDao.findById(productId);
            products.get().setProductName(getProductNameFromRestService(productId).getProductName());
            products.get().setPrice(getPriceFromRestService(productId));
        } catch (DataAccessResourceFailureException e) {
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }
        catch (NoSuchElementException e) {
            logger.error(e.getMessage(),e);
            throw new ResourceNotFoundException(productId.toString());
        }

        logger.info("Successfully retrieved the products based on productId", productId);
        return products;
    }

    private Price getPriceFromRestService(Long productId) throws RestClientException{
        Price price = null;
        try {
            String url = MessageFormat.format(priceServiceUrl, productId);
            price = restTemplate.getForObject(url, Price.class);
        }catch (ResourceNotFoundException e){
            logger.error("Error",e);
            throw new ResourceNotFoundException(productId.toString());
        }catch (RestClientException e){
            logger.error("Throwing Rest Client exception",e);
            throw new ServiceException(ErrorCode.SERVICE_UNAVAILABLE.getCode(),ErrorCode.SERVICE_UNAVAILABLE.getDescription());
        }
        return price;
    }

    private ProductName getProductNameFromRestService(Long productId) throws RestClientException{
        ProductName productName = null;
        try {
            String url = MessageFormat.format(productNameServiceUrl, productId);
            productName = restTemplate.getForObject(url, ProductName.class);
        }catch (ResourceNotFoundException e){
            logger.error("Error",e);
            throw new ResourceNotFoundException(productId.toString());
        }catch (RestClientException e){
            logger.error("Throwing Rest Client exception",e);
            throw new ServiceException(ErrorCode.SERVICE_UNAVAILABLE.getCode(),ErrorCode.SERVICE_UNAVAILABLE.getDescription());
        }
        return productName;
    }

}
