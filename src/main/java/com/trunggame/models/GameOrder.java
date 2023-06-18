package com.trunggame.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "`game_order`")
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "code")
    private String code;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "status")
    private String status = "1";
    // 1 - cho xu ly, 2 - dang xu ly, 3 - thanh cong , 4 - Huỷ
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}

