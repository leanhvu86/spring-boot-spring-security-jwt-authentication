package com.trunggame.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`game_order_detail`")
public class GameOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_order_id", nullable = false)
    private Long gameOrderId;

    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "character_name", columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String characterName;

    @Column(name = "promotion_percent")
    private BigDecimal promotionPercent;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "status")
    private String status = "0";

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "server_name")
    private String serverName;

    @Column(name = "account")
    private String account;

    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "login_type")
    private String loginType;

    @Column(name = "password")
    private String password;

    @Column(name = "login_code")
    private String loginCode;

    @Column(name = "description", length = 1000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}

