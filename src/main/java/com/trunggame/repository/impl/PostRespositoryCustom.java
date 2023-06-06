package com.trunggame.repository.impl;

import com.trunggame.dto.GamePackageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRespositoryCustom {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<GamePackageDTO> getAllPackage() {

        String sql = "SELECT   gp.id ,gp.description_vi,gp.description_en ,gp.attribute ,gp.game_id ,gp.name ,gp.price ,gp.rating ,gp.warehouse_quantity ,gp.unit ,gp.trade_count, f.preview_url  from game_package gp \n" +
                        "\tjoin file f on f.uniq_id  =gp.image_id ";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> GamePackageDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getLong("price"))
                .unit(rs.getString("unit"))
                .warehouseQuantity(rs.getInt("warehouse_quantity"))
                .tradeCount(rs.getInt("trade_count"))
                .rating(rs.getLong("rating"))
                .attribute(rs.getString("attribute"))
                .previewUrl(rs.getString("preview_url"))
                .build());
    }

}
