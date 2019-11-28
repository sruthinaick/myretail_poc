package com.myRetail.myRetailDemo.controllertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.myRetailDemo.constants.Currency;
import com.myRetail.myRetailDemo.controller.MyRetailPriceController;
import com.myRetail.myRetailDemo.controller.MyRetailProductController;
import com.myRetail.myRetailDemo.dao.PriceDao;
import com.myRetail.myRetailDemo.dao.ProductDao;
import com.myRetail.myRetailDemo.dao.ProductNameDao;
import com.myRetail.myRetailDemo.entity.Price;
import com.myRetail.myRetailDemo.entity.Product;
import com.myRetail.myRetailDemo.entity.ProductName;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.service.price.PriceGetService;
import com.myRetail.myRetailDemo.service.product.ProductCreateService;
import com.myRetail.myRetailDemo.service.product.ProductGetService;
import com.myRetail.myRetailDemo.service.product.ProductUpdateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = MyRetailProductController.class)
public class MyRetailProductControllerTests {
    private static final Logger logger = LogManager.getLogger(MyRetailProductController.class);
    private static final Long productId = 12345l;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PriceGetService priceGetService;
    @MockBean
    private ProductGetService productGetService;
    @MockBean
    private ProductCreateService productCreateService;
    @MockBean
    private ProductUpdateService productUpdateService;
    @MockBean
    private ProductDao productDao;
    @MockBean
    private PriceDao priceDao;
    @MockBean
    private ProductNameDao productNameDao;
    @InjectMocks
    private MyRetailPriceController myRetailPriceController;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        //mockMvc = MockMvcBuilders.standaloneSetup(MyRetailProductControllerTests.class).build();
    }

    @Test
    public void product_getall_Service_validData() throws Exception {
        logger.info("---- Started test product_getall_Service_validData ----");
        Product product = new Product();

        Price price = new Price();
        price.setProductId(12345l);
        price.setPriceId(120l);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        ProductName productName = new ProductName();
        productName.setProductId(12345l);
        productName.setProductNameId(11111l);
        productName.setProductName("ProductName");

        product.setProductId(12345l);
        product.setPrice(price);
        product.setProductName("ProductName");

        List<Product> productList = new ArrayList<Product>();
        productList.add(product);
        Mockito.when(productGetService.getAllproducts()).thenReturn(productList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/allproducts").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "[{\"productId\":12345,\"productName\":\"ProductName\",\"price\":{\"productId\":12345,\"maxRetailPrice\":900.0,\"offerPrice\":1300.0,\"currency\":\"RUPEES\"}}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        logger.info("---- Ended test product_getall_Service_validData ----");

    }

    @Test
    public void product_get_Service_validData() throws Exception {
        logger.info("---- Started test product_get_Service_validData ----");
        Product product = new Product();

        Price price = new Price();
        price.setProductId(12345l);
        price.setPriceId(120l);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        ProductName productName = new ProductName();
        productName.setProductId(12345l);
        productName.setProductNameId(11111l);
        productName.setProductName("ProductName");

        product.setProductId(12345l);
        product.setPrice(price);
        product.setProductName("ProductName");

        Mockito.when(productGetService.getProductById(productId)).thenReturn(Optional.of(product));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/products/12345").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"productId\":12345,\"productName\":\"ProductName\",\"price\":{\"productId\":12345,\"maxRetailPrice\":900.0,\"offerPrice\":1300.0,\"currency\":\"RUPEES\"}}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        logger.info("---- Ended test product_get_Service_validData ----");

    }

    @Test
    public void product_get_service_invalidData1() throws Exception {
        logger.info("---- Started test product_get_service_invalidData1 ----");
        Product product = new Product();
        Mockito.when(productGetService.getProductById(productId)).thenThrow(ResourceNotFoundException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/products/12345").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());

        logger.info("---- Ended test product_get_service_invalidData1 ----");

    }

    @Test
    public void product_get_service_invalidData2() throws Exception {
        logger.info("---- Started test product_get_service_invalidData2 ----");
        Product product = new Product();
        Mockito.when(productGetService.getProductById(productId)).thenThrow(ServiceException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/products/12345").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), result.getResponse().getStatus());

        logger.info("---- Ended test product_get_service_invalidData2 ----");

    }

    @Test
    public void product_post_Service_validData() throws Exception {
        logger.info("---- Started test product_post_Service_validData ----");
        Product product = new Product();

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        product.setProductId(12345l);
        product.setProductName("ProductName");
        product.setPrice(price);

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);
        Mockito.when(productCreateService.createProduct(Mockito.any(Product.class))).thenReturn(product);
         RequestBuilder requestBuilder = MockMvcRequestBuilders
                        .post("/myretail/products/")
                        .content(asJsonString(product))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON);
         MvcResult result = mockMvc.perform(requestBuilder).andReturn();
         MockHttpServletResponse response = result.getResponse();
         Assert.assertEquals(HttpStatus.OK.value(),response.getStatus());

        logger.info("---- Ended test product_post_Service_validData ----");

    }

    @Test
    public void product_post_Service_invalidData1() throws Exception {
        logger.info("---- Started test product_post_Service_invalidData1 ----");
        Product product = new Product();

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        product.setProductId(12345l);
        product.setProductName("ProductName");
        product.setPrice(price);

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);
        Mockito.when(productCreateService.createProduct(Mockito.any(Product.class))).thenThrow(ResourceNotFoundException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myretail/products/")
                .content(asJsonString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());

        logger.info("---- Ended test product_post_Service_invalidData1 ----");

    }

    @Test
    public void product_post_Service_invalidData2() throws Exception {
        logger.info("---- Started test product_post_Service_invalidData2 ----");
        Product product = new Product();

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        product.setProductId(12345l);
        product.setProductName("ProductName");
        product.setPrice(price);

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);
        Mockito.when(productCreateService.createProduct(Mockito.any(Product.class))).thenThrow(ServiceException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myretail/products/")
                .content(asJsonString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), result.getResponse().getStatus());

        logger.info("---- Ended test product_post_Service_invalidData2 ----");

    }

    @Test
    public void product_put_Service_validData() throws Exception {
        logger.info("---- Started test product_put_Service_validData ----");
        Product product = new Product();

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        product.setProductId(12345l);
        product.setProductName("ProductName");
        product.setPrice(price);

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);
        Mockito.doNothing().when(productUpdateService).updateProduct(Mockito.any(Long.class),Mockito.any(Product.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/myretail/products/12345")
                .content(asJsonString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(),response.getStatus());

        logger.info("---- Ended test product_put_Service_validData ----");

    }

    @Test
    public void product_put_Service_invalidData1() throws Exception {
        logger.info("---- Started test product_put_Service_invalidData1 ----");
        Product product = new Product();

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        product.setProductId(12345l);
        product.setProductName("ProductName");
        product.setPrice(price);

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);
        Mockito.doThrow(ResourceNotFoundException.class).when(productUpdateService).updateProduct(Mockito.any(Long.class),Mockito.any(Product.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/myretail/products/12345")
                .content(asJsonString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());

        logger.info("---- Ended test product_put_Service_invalidData1 ----");

    }

    @Test
    public void product_put_Service_invalidData2() throws Exception {
        logger.info("---- Started test product_put_Service_invalidData2 ----");
        Product product = new Product();

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        product.setProductId(12345l);
        product.setProductName("ProductName");
        product.setPrice(price);

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);
        Mockito.doThrow(ServiceException.class).when(productUpdateService).updateProduct(Mockito.any(Long.class),Mockito.any(Product.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/myretail/products/12345")
                .content(asJsonString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(),response.getStatus());

        logger.info("---- Ended test product_put_Service_invalidData2 ----");

    }



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
