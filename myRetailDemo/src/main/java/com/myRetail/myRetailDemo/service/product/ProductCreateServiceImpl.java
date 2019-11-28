package com.myRetail.myRetailDemo.service.product;

import com.myRetail.myRetailDemo.entity.Price;
import com.myRetail.myRetailDemo.entity.Product;
import com.myRetail.myRetailDemo.entity.ProductName;
import com.myRetail.myRetailDemo.exceptions.ErrorCode;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import java.util.List;

@Service
public class ProductCreateServiceImpl implements ProductCreateService{

    static private final XLogger logger = XLoggerFactory.getXLogger(ProductCreateServiceImpl.class);

    @Autowired
    private ProductDao productDao;

    private RestTemplate restTemplate = new RestTemplate();

    @Value("http://localhost:8080/myretail/products/price")
    private String priceServiceUrl;

    @Value("http://localhost:8080/myretail/products/productname")
    private String productNameServiceUrl;


    public Product createProduct(Product product){
        Product responseProduct = null;
        logger.info("Going to save a product {}",product);
        try{
            responseProduct = productDao.save(product);
            product.setProductId(responseProduct.getProductId());
            product.getPrice().setProductId(responseProduct.getProductId());
            createProductNameRestService(new ProductName(responseProduct.getProductId(),product.getProductName()));
            responseProduct.setPrice(createPriceRestService(product.getPrice()));
        }
        catch (DataAccessResourceFailureException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getCode(),ErrorCode.DATA_ACCESS_RESOURCE_FAILURE.getDescription());
        }

        logger.info("Successfully saved product {}",product);
        return responseProduct;
    }

    public void createProducts(List<Product> products){
        //productDAO.saveAll(products);
        logger.info("Going to save a list of products {}",products);
        for (Product product: products) {
            createProduct(product);
        }
        logger.info("Successfully saved a list of products {}",products);
    }

    private ProductName createProductNameRestService(ProductName productName) throws RestClientException {
        ProductName responseProductName = null;
        try {
            responseProductName = restTemplate.postForObject(productNameServiceUrl, productName, ProductName.class);
        }catch (ResourceNotFoundException e){
            logger.error("Error",e);
            throw new ResourceNotFoundException(null);
        }catch (RestClientException e){
            logger.error("Throwing Rest Client exception",e);
            throw new ServiceException(ErrorCode.SERVICE_UNAVAILABLE.getCode(),ErrorCode.SERVICE_UNAVAILABLE.getDescription());
        }
        return responseProductName;
    }

    private Price createPriceRestService(Price price) throws RestClientException {
        Price responsePrice = null;
        try {
            responsePrice = restTemplate.postForObject(priceServiceUrl, price, Price.class);
        }catch (ResourceNotFoundException e){
            logger.error("Error",e);
            throw new ResourceNotFoundException(null);
        }catch (RestClientException e){
            logger.error("Throwing Rest Client exception",e);
            throw new ServiceException(ErrorCode.SERVICE_UNAVAILABLE.getCode(),ErrorCode.SERVICE_UNAVAILABLE.getDescription());
        }
        return responsePrice;
    }
}
