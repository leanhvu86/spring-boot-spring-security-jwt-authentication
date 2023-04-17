package com.trunggame.repository.impl;

import com.trunggame.dto.GameInformationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameRepositoryCustom {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<GameInformationDTO> getAllInformation() {

        String sql =
                "SELECT s.id," +
                        "       s.name," +
                        "       s.description," +
                        "       s.status," +
                        "       s.type AS gameType," +
                        "       s.company_name AS companyName," +
                        "       s.thumbnail AS thumbnail," +
                        "       s.market_type AS marketType," +
                        "       s.youtube_link AS youtubeLink," +
                        "       s.content_en AS contentEn," +
                        "       f.preview_url AS imageUrl," +
                        "       p.price," +
                        "       p.promotion_price AS promotionPrice," +
                        "       p.promotion_percent AS promotionPercent," +
                        "       gc.id AS categoryId," +
                        "       gc.name AS categoryName," +
                        "       s.content_vi AS contentVi," +
                        "       s.description_en AS descriptionEn," +
                        "       tmp.tags " +
                        "       FROM game AS s" +
                        "         LEFT JOIN file AS f ON f.owner_id = s.id" +
                        "         JOIN game_categories AS gc ON gc.id = s.category_id" +
                        "         LEFT JOIN price AS p ON p.game_id = s.id" +
                        "         LEFT JOIN (" +
                        "    SELECT stg.game_id, GROUP_CONCAT(CONCAT(st.name, '-', st.color) SEPARATOR ';') AS tags" +
                        "    FROM smart_tag st" +
                        "             JOIN smart_tag_game stg ON st.id = stg.tag_id" +
                        "    GROUP BY stg.game_id" +
                        ") AS tmp ON tmp.game_id = s.id where gc.status = 'ACTIVE' ";

        System.out.println(sql);

        return jdbcTemplate.query(sql, (rs, rowNum) -> GameInformationDTO.
                builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .status(rs.getString("status"))
                .type(rs.getString("gameType"))
                .imageUrl(rs.getString("imageUrl"))
                .price(rs.getBigDecimal("price"))
                .promotionPercent(rs.getBigDecimal("promotionPercent"))
                .promotionPrice(rs.getBigDecimal("promotionPrice"))
                .categoryId(rs.getLong("categoryId"))
                .marketType(rs.getString("marketType"))
                .categoryName(rs.getString("categoryName"))
                .companyName(rs.getString("companyName"))
                .youtubeLink(rs.getString("youtubeLink"))
                .thumbnailUrl(rs.getString("thumbnail"))
                .contentEn(rs.getString("contentEn"))
                .contentVi(rs.getString("contentVi"))
                .descriptionEn(rs.getString("descriptionEn"))
                .tags(rs.getString("tags"))
                .build());
    }
}
