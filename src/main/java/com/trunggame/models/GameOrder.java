package com.trunggame.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`game_order`")
public class GameOrder {
    @Id
    private Long id;

    @Column(name = "game_id", nullable = false)
    private Integer gameId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "character_name")
    private String character_name;

    @Column(name = "promotion_percent")
    private BigDecimal promotionPercent;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "server_name")
    private String serverName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}

