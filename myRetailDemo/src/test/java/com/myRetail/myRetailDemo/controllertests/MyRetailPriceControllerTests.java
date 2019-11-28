package com.myRetail.myRetailDemo.controllertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.myRetailDemo.constants.Currency;
import com.myRetail.myRetailDemo.controller.MyRetailPriceController;
import com.myRetail.myRetailDemo.dao.PriceDao;
import com.myRetail.myRetailDemo.dao.ProductDao;
import com.myRetail.myRetailDemo.dao.ProductNameDao;
import com.myRetail.myRetailDemo.entity.Price;
import com.myRetail.myRetailDemo.entity.Product;
import com.myRetail.myRetailDemo.entity.ProductName;
import com.myRetail.myRetailDemo.exceptions.ResourceNotFoundException;
import com.myRetail.myRetailDemo.exceptions.ServiceException;
import com.myRetail.myRetailDemo.service.price.PriceCreateService;
import com.myRetail.myRetailDemo.service.price.PriceGetService;
import com.myRetail.myRetailDemo.service.price.PriceUpdateService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = MyRetailPriceController.class)
public class MyRetailPriceControllerTests {

    private static final Logger logger = LogManager.getLogger(MyRetailPriceController.class);
    private static final long productId = 12345;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PriceGetService priceGetService;
    @MockBean
    private PriceCreateService priceCreateService;
    @MockBean
    private PriceUpdateService priceUpdateService;
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
    public void price_create_Service_validData() throws Exception {
        logger.info("---- Started test price_get_Service_validData ----");

        Price price = new Price();
        price.setProductId(12345l);
        price.setPriceId(120l);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        Mockito.when(priceGetService.getPriceById(productId)).thenReturn(Optional.of(price));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/products/12345/price").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"productId\":12345,\"maxRetailPrice\":900.0,\"offerPrice\":1300.0,\"currency\":RUPEES}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        logger.info("---- Ended test price_get_Service_validData ----");

    }

    @Test
    public void price_create_Service_validData1() throws Exception {
        logger.info("---- Started test price_get_Service_validData1 ----");

        Mockito.when(priceGetService.getPriceById(productId)).thenThrow(ResourceNotFoundException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/products/12345/price").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"productId\":12345,\"maxRetailPrice\":900.0,\"offerPrice\":1300.0,\"currency\":RUPEES}";
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());

        logger.info("---- Ended test price_get_Service_invalidData1 ----");

    }

    @Test
    public void price_create_Service_validData2() throws Exception {
        logger.info("---- Started test price_get_Service_validData2 ----");

        Mockito.when(priceGetService.getPriceById(productId)).thenThrow(ServiceException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/myretail/products/12345/price").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"productId\":12345,\"maxRetailPrice\":900.0,\"offerPrice\":1300.0,\"currency\":RUPEES}";
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), result.getResponse().getStatus());

        logger.info("---- Ended test price_get_Service_invalidData2 ----");

    }

    @Test
    public void price_post_Service_validData() throws Exception {
        logger.info("---- Started test price_post_Service_validData ----");

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        Mockito.when(priceCreateService.createPrice(Mockito.any(Price.class))).thenReturn(price);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myretail/products/price")
                .content(asJsonString(price))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(),response.getStatus());

        logger.info("---- Ended test price_post_Service_validData ----");

    }

    @Test
    public void price_post_Service_invalidData1() throws Exception {
        logger.info("---- Started test price_post_Service_invalidData1 ----");

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        Mockito.when(priceCreateService.createPrice(Mockito.any(Price.class))).thenThrow(ResourceNotFoundException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myretail/products/price")
                .content(asJsonString(price))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());

        logger.info("---- Ended test price_post_Service_invalidData1 ----");

    }

    @Test
    public void price_post_Service_invalidData2() throws Exception {
        logger.info("---- Started test price_post_Service_invalidData2 ----");

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        Mockito.when(priceCreateService.createPrice(Mockito.any(Price.class))).thenThrow(ServiceException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myretail/products/price")
                .content(asJsonString(price))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(),response.getStatus());

        logger.info("---- Ended test price_post_Service_invalidData2 ----");

    }

    @Test
    public void price_put_Service_validData() throws Exception {
        logger.info("---- Started test price_post_Service_validData ----");

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        Mockito.when(priceUpdateService.updatePrice(Mockito.any(Long.class),Mockito.any(Price.class))).thenReturn(price);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/myretail/products/12345/price")
                .content(asJsonString(price))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(),response.getStatus());

        logger.info("---- Ended test price_put_Service_validData ----");

    }

    @Test
    public void price_put_Service_invalidData1() throws Exception {
        logger.info("---- Started test price_put_Service_invalidData1 ----");

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        Mockito.doThrow(ResourceNotFoundException.class).when(priceUpdateService).updatePrice(Mockito.any(Long.class),Mockito.any(Price.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/myretail/products/12345/price")
                .content(asJsonString(price))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatus());

        logger.info("---- Ended test price_put_Service_invalidData1 ----");

    }

    @Test
    public void price_put_Service_invalidData2() throws Exception {
        logger.info("---- Started test price_put_Service_invalidData2 ----");

        Price price = new Price();
        price.setProductId(12345L);
        price.setPriceId(120L);
        price.setOfferPrice(1300f);
        price.setMaxRetailPrice(900f);
        price.setCurrency(Currency.RUPEES);

        Mockito.doThrow(ServiceException.class).when(priceUpdateService).updatePrice(Mockito.any(Long.class),Mockito.any(Price.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/myretail/products/12345/price")
                .content(asJsonString(price))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(),response.getStatus());

        logger.info("---- Ended test price_put_Service_invalidData2 ----");

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
