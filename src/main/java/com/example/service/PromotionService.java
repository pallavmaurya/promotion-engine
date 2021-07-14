package com.example.service;

import com.example.data.Promotion;

import java.util.List;

public interface PromotionService {

    /**
     * Fetch list of all active {@link Promotion} from {@link PromotionRepository}.
     *
     * @return List of all active {@link Promotion}.
     */
    List<Promotion> getActivePromotions();
}
