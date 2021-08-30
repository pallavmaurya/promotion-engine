package com.example.controller;

import com.example.TestDataSetup;
import com.example.service.CheckoutService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openapitools.model.ShoppingCart;
import org.openapitools.model.StockKeepingUnits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CheckoutController.class})
@WebMvcTest(controllers = CheckoutController.class)
public class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectmapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CheckoutService checkoutService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void checkout() throws Exception {

        StockKeepingUnits stockKeepingUnits = TestDataSetup.getStockKeepingUnits();
        ShoppingCart shoppingCart = TestDataSetup.getShoppingCart();
        when(checkoutService.checkout(any())).thenReturn(shoppingCart);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/promotionengine/v1/checkout")
                        .content(getObjectAsJson(stockKeepingUnits))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCart actualShoppingCart = objectmapper.readValue(result.getResponse().getContentAsString(), ShoppingCart.class);
        assertNotNull(actualShoppingCart);
        assertEquals(1, actualShoppingCart.getShoppingCartItems().size());
        assertEquals(10, actualShoppingCart.getShoppingCartItems().get(0).getCartItemPrice().intValue());
        assertNull(actualShoppingCart.getShoppingCartItems().get(0).getPromotionName());
        assertEquals('A', actualShoppingCart.getShoppingCartItems().get(0).getStockKeepingUnit().getSkuId().charAt(0));
        assertEquals(java.util.Optional.of(1).get(), actualShoppingCart.getShoppingCartItems().get(0).getStockKeepingUnit().getQuantity());
    }

    private String getObjectAsJson(StockKeepingUnits stockKeepingUnits) throws JsonProcessingException {
        return objectmapper.writeValueAsString(stockKeepingUnits);
    }
}