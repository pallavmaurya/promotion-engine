package com.example.service;

import com.example.data.SkuPrice;

public interface PriceService {

    /**
     * Fetch SkuPrice for input skuId
     *
     * @param skuId : {@link SkuPrice#skuId}
     * @return SkuPrice for input skuId
     */
    SkuPrice getSkuPrice(Character skuId);

}
