package com.trunggame.repository;

import com.trunggame.models.Role;
import com.trunggame.models.SmartTag;
import com.trunggame.models.SmartTagGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmartTagGameRepository  extends JpaRepository<SmartTagGame, Long> {

    @Query(value = "SELECT * FROM smart_tag_game WHERE game_id = :gameId ", nativeQuery = true)
    Optional<List<SmartTagGame>> findByGameId(Long gameId);

    @Query(value = "select st.* from smart_tag st " +
            "join smart_tag_game stg on st.id = stg.tag_id " +
            "where game_id = :gameId", nativeQuery = true)
    List<SmartTag> findTagByGameId(Long gameId);
}
