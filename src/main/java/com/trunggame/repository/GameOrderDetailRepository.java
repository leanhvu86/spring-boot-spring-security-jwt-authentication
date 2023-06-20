package com.trunggame.repository;

import com.trunggame.models.GameOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GameOrderDetailRepository extends JpaRepository<GameOrderDetail, Long> {
    List<GameOrderDetail> findAllByGameId(Long gameId);

    List<GameOrderDetail> findAllByGameOrderId(Long gameOrderId);

    @Transactional
    @Modifying
    void deleteAllByGameOrderId(Long gameOrderId);

}