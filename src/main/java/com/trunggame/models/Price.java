package com.trunggame.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;

import com.trunggame.enums.ECommonStatus;
import lombok.*;

@Entity
@Table(name = "price")
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_id")
    private Long gameId;

    @NonNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "promotion_price", precision = 10, scale = 2)
    private BigDecimal promotionPrice;

    @Column(name = "promotion_percent", precision = 5, scale = 2)
    private BigDecimal promotionPercent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ECommonStatus status = ECommonStatus.ACTIVE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}


