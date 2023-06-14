package com.trunggame.dto;

import com.trunggame.models.GameServerGroup;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Getter
@Setter
public class GameInformationDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String hot;
    private String type;
    private Long categoryId;
    private String categoryName;
    private String imageUrl;
    private String thumbnailUrl;
    private BigDecimal price;
    private BigDecimal promotionPrice;
    private BigDecimal promotionPercent;
    private String companyName;
    private String marketType;
    private String youtubeLink;
    private String contentEn;
    private String contentVi;
    private String descriptionEn;
    private String gamePriority;
    private List<String> tags;
    private List<GamePackageDTO> gamePackages;
    private List<GameServerGroup> server;

}
