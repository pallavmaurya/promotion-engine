package com.example.controller;

import com.example.model.ShoppingCart;
import com.example.model.StockKeepingUnits;
import com.example.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/promotionengine/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;


    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<ShoppingCart> checkout( @RequestBody StockKeepingUnits stockKeepingUnits) {
        return ResponseEntity.ok(this.checkoutService.checkout(stockKeepingUnits.getStockKeepingUnits()));
    }
}
