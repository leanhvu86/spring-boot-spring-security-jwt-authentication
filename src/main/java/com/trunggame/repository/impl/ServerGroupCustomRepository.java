package com.trunggame.repository.impl;

import com.trunggame.dto.ServerGroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServerGroupCustomRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ServerGroupDTO> getAllServerGroup() {

        String sql =
                "select sg.id as id, sg.name as name, sg.parent_id as parentId, parentSG.name as parentName from server_group as sg "
                        + " join server_group as parentSG on sg.parent_id = parentSG.id;";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> ServerGroupDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .parentId(rs.getLong("parentId"))
                .parentName(rs.getString("parentName"))
                .build());
    }

}
