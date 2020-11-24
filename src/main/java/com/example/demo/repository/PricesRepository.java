package com.example.demo.repository;

import com.example.demo.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricesRepository extends JpaRepository<Prices, Integer> {
}
