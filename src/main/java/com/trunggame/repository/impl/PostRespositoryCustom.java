package com.trunggame.repository.impl;

import com.trunggame.dto.BannerDTO;
import com.trunggame.dto.GamePackageDTO;
import com.trunggame.models.Banner;
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

    public List<BannerDTO> getAllBanner() {

        String sql = "select banner0_.id as id, banner0_.created_at as created_at, banner0_.file_id as file_id" +
                            ", banner0_.image_url as image_url, banner0_.priority as priority, banner0_.status as status, " +
                            "banner0_.updated_at as updated_at from banner banner0_\n" +
                        "order by status asc , priority asc";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> BannerDTO.
                builder()
                .id(rs.getLong("id"))
                .imageUrl(rs.getString("image_url"))
                .priority(rs.getString("priority"))
                .fileId(rs.getString("file_id"))
                .status(rs.getString("status"))
                .build());
    }

    public List<BannerDTO> getAllActiveBanner() {

        String sql = "select banner0_.id as id, banner0_.created_at as created_at, banner0_.file_id as file_id" +
                            ", banner0_.image_url as image_url, banner0_.priority as priority, banner0_.status as status, " +
                            "banner0_.updated_at as updated_at from banner banner0_\n" +
                        "where status ='ACTIVE' order by priority asc";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> BannerDTO.
                builder()
                .id(rs.getLong("id"))
                .fileId(rs.getString("file_id"))
                .imageUrl(rs.getString("image_url"))
                .priority(rs.getString("priority"))
                .status(rs.getString("status"))
                .build());
    }

}
