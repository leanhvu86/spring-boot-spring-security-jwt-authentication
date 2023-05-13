package com.trunggame.dto;

import com.trunggame.models.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailInputDTO {
    private Long id;
    private Long gameId;
    private Integer quantity;
    private BigDecimal amount;
    private BigDecimal price;
    private Long packageId;
    private String server;
    private String loginType;
    private String password;
    private String characterName;
    private String account;
    private String loginCode;
}
