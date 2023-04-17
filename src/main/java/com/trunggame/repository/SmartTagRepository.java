package com.trunggame.repository;

import com.trunggame.models.SmartTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmartTagRepository extends JpaRepository<SmartTag, Long> {
    Optional<SmartTag> findFirstByName(String name);

    @Transactional
    @Modifying
    void deleteAllByIdIn(List<Long> ids);

}
