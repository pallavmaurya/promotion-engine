package com.example.repository;

import com.example.data.Promotion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, String> {

    List<Promotion> findAllByActiveIsTrue();

}
