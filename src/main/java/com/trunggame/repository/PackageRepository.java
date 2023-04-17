package com.trunggame.repository;

import com.trunggame.models.GamePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<GamePackage, Long> {
    boolean existsByGameId(String gameId);

    List<GamePackage> findAllByGameId(Long gameId);
}