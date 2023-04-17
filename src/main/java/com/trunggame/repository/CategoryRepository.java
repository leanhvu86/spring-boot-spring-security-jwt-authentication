package com.trunggame.repository;

import com.trunggame.enums.ECategoryStatus;
import com.trunggame.models.GameCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<GameCategories, Long> {

    @Query(name = "SELECT * FROM game_categories WHERE name = :name and status =  :status", nativeQuery = true)
    Optional<GameCategories> findOneByNameAndStatus(String name, ECategoryStatus status);
    Optional<List<GameCategories>> findAllByStatus(ECategoryStatus status);
    Optional<List<GameCategories>> findAllByParentId(Long parentId);
    Optional<GameCategories> findFirstByName(String name);
}
