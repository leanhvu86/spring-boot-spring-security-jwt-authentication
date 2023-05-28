package com.trunggame.repository;

import com.trunggame.models.GameServerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameServerGroupRepository extends JpaRepository<GameServerGroup, Long> {
    List<GameServerGroup> findAllByGameId(Long gameId);
    List<GameServerGroup> findAllByPackageId(Long gameId);
}
