package com.trunggame.repository;

import com.trunggame.models.ServerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServerGroupRepository extends JpaRepository<ServerGroup, Long> {

    List<ServerGroup> findAllByIdIn(List<Long> ids);
// hiên tại bỏ vi đang dùng GameServerGroup
}
