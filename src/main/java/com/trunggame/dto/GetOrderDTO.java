package com.trunggame.dto;

import com.trunggame.models.GameOrder;
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
public class GetOrderDTO {
    private String server;
    private String code;
    private String loginType;
    private String characterName;
    private String orderCode;
    private String orderBy;
    private String orderType;
    private String pageSize;
    private String pageNumber;
    private Long orderId;
    private Long customerId;
    private String status;
    private BigDecimal totalAmount;
    private List<GameOrder> orderList;
    private Long totalData;
}
