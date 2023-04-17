package com.trunggame.repository;

import com.trunggame.models.MarketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketTypeRepository extends JpaRepository<MarketType, Long> {
}
