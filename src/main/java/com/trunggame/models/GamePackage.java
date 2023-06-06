package com.trunggame.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`game_package`")
public class GamePackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String name;

    private double price;

    private String unit;

    private double rating;

    private String serverGroup;

    @Column(name = "attribute",columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String attribute;

    private int warehouseQuantity;

    private int tradeCount;

    @Column(name = "description_vi",columnDefinition = "VARCHAR CHARACTER SET utf8")
    private String descriptionVi;

    @Column(name = "description_en",columnDefinition = "VARCHAR CHARACTER SET utf8")
    private String descriptionEn;

    private int deliveryTime;

    private Long gameId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GamePackage.Status status = GamePackage.Status.ACTIVE;

    @Column(name = "image_id")
    private String imageId;

    @Transient
    private String previewUrl;

    @Transient
    private List<GameServerGroup> server;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GamePackage.TopSaleStatus topSale = TopSaleStatus.INACTIVE;

    public enum Status {
        ACTIVE,
        INACTIVE
    }
    public enum TopSaleStatus {
        ACTIVE,
        INACTIVE
    }
}
