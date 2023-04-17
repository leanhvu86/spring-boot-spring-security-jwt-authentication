package com.trunggame.repository;

import com.trunggame.models.Price;
import com.trunggame.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
