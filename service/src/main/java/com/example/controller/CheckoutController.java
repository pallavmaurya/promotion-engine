package com.example.controller;

import com.example.service.CheckoutService;
import com.example.validation.UniqueSkuConstraint;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.ShoppingCart;
import org.openapitools.model.StockKeepingUnits;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/promotionengine/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;


    /**
     * POST /promotionengine/v1/checkout : Checkout list of StockKeepingUnits to get a ShoppingCartItems response
     *
     * @param stockKeepingUnits List of user object (required)
     * @return successful operation (status code 200)
     * or Invalid input (status code 400)
     */
    @ApiOperation(value = "Checkout list of StockKeepingUnits to get a ShoppingCartItems response", nickname = "checkout", notes = "", response = ShoppingCart.class, responseContainer = "List", tags = {"promotionengine",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful checkout", response = ShoppingCart.class),
            @ApiResponse(code = 400, message = "Invalid input", response = String.class)})
    @RequestMapping(produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<ShoppingCart> checkout(@ApiParam(value = "StockKeepingUnits", required = true) @UniqueSkuConstraint @Valid @RequestBody StockKeepingUnits stockKeepingUnits) {
        return ResponseEntity.ok(this.checkoutService.checkout(stockKeepingUnits.getStockKeepingUnits()));
    }
}
