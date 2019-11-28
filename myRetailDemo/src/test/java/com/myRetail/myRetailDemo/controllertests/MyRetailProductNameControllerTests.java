package com.myRetail.myRetailDemo.controllertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.myRetailDemo.constants.Currency;
import com.myRetail.myRetailDemo.controller.MyRetailPriceController;
import com.myRetail.myRetailDemo.controller.MyRetailProductNameController;
import com.myRetail.myRetailDemo.dao.PriceDao;
import com.myRetail.myRetailDemo.dao.ProductDao;
import com.myRetail.myRetailDemo.dao.ProductNameDao;
import com.myRetail.myRetailDemo.entity.Price;
import com.myRetail.myRetailDemo.entity.ProductName;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.service.price.PriceCreateService;
import com.myRetail.myRetailDemo.service.price.PriceGetService;
import com.myRetail.myRetailDemo.service.price.PriceUpdateService;
import com.myRetail.myRetailDemo.service.productName.ProductNameCreateService;
import com.myRetail.myRetailDemo.service.productName.ProductNameGetService;
import com.myRetail.myRetailDemo.service.productName.ProductNameUpdateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = MyRetailProductNameController.class)
public class MyRetailProductNameControllerTests {

    private static final Logger logger = LogManager.getLogger(MyRetailProductNameController.class);
    private static final long productId = 12345;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductNameGetService productNameGetService;
    @MockBean
    private ProductNameCreateService productNameCreateService;
    @MockBean
    private ProductNameUpdateService productNameUpdateService;
    @MockBean
    private ProductDao productDao;
    @MockBean
    private PriceDao priceDao;
    @MockBean
    private ProductNameDao productNameDao;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void productName_create_Service_validData() throws Exception {
        logger.info("---- Started test productName_get_Service_validData ----");

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);

        Mockito.when(productNameGetService.getProductNameById(productId)).thenReturn(Optional.of(productName));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/products/12345/productname").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"productId\":12345,\"productName\":\"ProductName\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        logger.info("---- Ended test productName_get_Service_validData ----");

    }

    @Test
    public void productName_create_Service_validData1() throws Exception {
        logger.info("---- Started test productName_get_Service_validData1 ----");

        Mockito.when(productNameGetService.getProductNameById(productId)).thenThrow(ResourceNotFoundException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/products/12345/productname").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"productId\":12345,\"productName\":\"ProductName\"}";
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());

        logger.info("---- Ended test price_get_Service_invalidData1 ----");

    }

    @Test
    public void productName_create_Service_validData2() throws Exception {
        logger.info("---- Started test productName_get_Service_validData2 ----");

        Mockito.when(productNameGetService.getProductNameById(productId)).thenThrow(ServiceException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/products/12345/productname").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"productId\":12345,\"productName\":\"ProductName\"}";
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), result.getResponse().getStatus());

        logger.info("---- Ended test productName_get_Service_invalidData2 ----");

    }

    @Test
    public void productName_post_Service_validData() throws Exception {
        logger.info("---- Started test productName_post_Service_validData ----");

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);

        Mockito.when(productNameCreateService.createProductName(Mockito.any(ProductName.class))).thenReturn(productName);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myretail/products/productname")
                .content(asJsonString(productName))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(),response.getStatus());

        logger.info("---- Ended test productName_post_Service_validData ----");

    }

    @Test
    public void productName_post_Service_invalidData1() throws Exception {
        logger.info("---- Started test productName_post_Service_invalidData1 ----");

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);

        Mockito.when(productNameCreateService.createProductName(Mockito.any(ProductName.class))).thenThrow(ResourceNotFoundException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myretail/products/productname")
                .content(asJsonString(productName))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());

        logger.info("---- Ended test productName_post_Service_invalidData1 ----");

    }

    @Test
    public void productName_post_Service_invalidData2() throws Exception {
        logger.info("---- Started test productName_post_Service_invalidData2 ----");

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);

        Mockito.when(productNameCreateService.createProductName(Mockito.any(ProductName.class))).thenThrow(ServiceException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myretail/products/productname")
                .content(asJsonString(productName))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(),response.getStatus());

        logger.info("---- Ended test productName_post_Service_invalidData2 ----");

    }

    @Test
    public void productName_put_Service_validData() throws Exception {
        logger.info("---- Started test productName_post_Service_validData ----");

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);

        Mockito.when(productNameUpdateService.updateProductName(Mockito.any(Long.class),Mockito.any(ProductName.class))).thenReturn(productName);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/myretail/products/12345/productname")
                .content(asJsonString(productName))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(),response.getStatus());

        logger.info("---- Ended test productName_put_Service_validData ----");

    }

    @Test
    public void productName_put_Service_invalidData1() throws Exception {
        logger.info("---- Started test productName_put_Service_invalidData1 ----");

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);

        Mockito.doThrow(ResourceNotFoundException.class).when(productNameUpdateService).updateProductName(Mockito.any(Long.class),Mockito.any(ProductName.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/myretail/products/12345/productname")
                .content(asJsonString(productName))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());

        logger.info("---- Ended test productName_put_Service_invalidData1 ----");

    }

    @Test
    public void productName_put_Service_invalidData2() throws Exception {
        logger.info("---- Started test productName_put_Service_invalidData2 ----");

        ProductName productName = new ProductName();
        productName.setProductId(12345L);
        productName.setProductName("ProductName");
        productName.setProductNameId(11111L);

        Mockito.doThrow(ServiceException.class).when(productNameUpdateService).updateProductName(Mockito.any(Long.class),Mockito.any(ProductName.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/myretail/products/12345/productname")
                .content(asJsonString(productName))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(),response.getStatus());

        logger.info("---- Ended test productName_put_Service_invalidData2 ----");

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
