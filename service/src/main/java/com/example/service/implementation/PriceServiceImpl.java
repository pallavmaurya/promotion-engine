package com.example.service.implementation;

import com.example.data.SkuPrice;
import com.example.exception.SkuPriceNotFoundException;
import com.example.repository.PriceRepository;
import com.example.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * This class implements methods exposed by {@link PriceService}
 *
 * @author pallavmaurya@gmail.com
 */
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    /**
     * Fetch SkuPrice for input skuId
     *
     * @param skuId : {@link SkuPrice#skuId}
     * @return SkuPrice for input skuId
     */
    @Override
    public SkuPrice getSkuPrice(Character skuId) {
        return this.priceRepository.findById(Character.toUpperCase(skuId))
                .orElseThrow(SkuPriceNotFoundException::new);
    }
}
