package com.trunggame.repository;

import com.trunggame.models.GameOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameOrderRepository extends JpaRepository<GameOrder, Long> {
    Page<GameOrder> findAll(Specification<GameOrder> spec, Pageable pageable);

    List<GameOrder> findByStatus(String status);
}