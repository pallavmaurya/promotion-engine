package com.example.repository;

import com.example.data.SkuPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends CrudRepository<SkuPrice, Character> {

}
