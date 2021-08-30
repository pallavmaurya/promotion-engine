package com.example.service.implementation;

import com.example.data.Promotion;
import com.example.repository.PromotionRepository;
import com.example.service.PriceService;
import com.example.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * This class implements methods exposed by {@link PriceService}
 *
 * @author pallavmaurya@gmail.com
 */
@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    /**
     * Fetch list of all active {@link Promotion} from {@link PromotionRepository}.
     *
     * @return List of all active {@link Promotion}.
     */
    @Override
    public List<Promotion> getActivePromotions() {
        return this.promotionRepository.findAllByActiveIsTrue();
    }
}
