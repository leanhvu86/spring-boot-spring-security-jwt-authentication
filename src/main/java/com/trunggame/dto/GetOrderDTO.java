package com.trunggame.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Builder
@Getter
@Setter
public class GetOrderDTO {
    private String server;
    private String loginType;
    private String characterName;
    private String orderCode;
    private String orderBy;
    private String orderType;
    private String pageSize;
    private String pageNumber;
    private Long orderId;
    private String status;
    private BigDecimal totalAmount;
}
