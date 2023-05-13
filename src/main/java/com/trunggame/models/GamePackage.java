package com.trunggame.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "name",columnDefinition = "NVARCHAR2(5000)")
    private String name;

    private double price;

    private String unit;

    private double rating;

    private String serverGroup;

    @Column(name = "server",columnDefinition = "NVARCHAR2(5000)")
    private String server;

    @Column(name = "attribute",columnDefinition = "NVARCHAR2(5000)")
    private String attribute;

    private int warehouseQuantity;

    private int tradeCount;

    @Column(name = "description_vi",columnDefinition = "NVARCHAR2(5000)")
    private String descriptionVi;

    private String descriptionEn;

    private int deliveryTime;

    private Long gameId;

    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
