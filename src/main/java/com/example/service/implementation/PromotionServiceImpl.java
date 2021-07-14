package com.example.service.implementation;

import com.example.data.Promotion;
import com.example.service.PromotionService;

import java.util.List;

public class PromotionServiceImpl  implements PromotionService {
    /**
     * Fetch list of all active {@link Promotion} from {@link PromotionRepository}.
     *
     * @return List of all active {@link Promotion}.
     */
    @Override
    public List<Promotion> getActivePromotions() {
        return null;
    }
}
