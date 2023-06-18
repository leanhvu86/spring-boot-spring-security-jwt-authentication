package com.trunggame.dto;

import com.trunggame.enums.EUserStatus;
import com.trunggame.models.*;
import lombok.*;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String code;
    private BigDecimal totalAmount;
    private BigDecimal totalAmountTrade;
    private Long customerId;
    private String customerName;
    private String phoneNumber;
    private String email;
    private Integer tradeCount;
    private String status;
    private EUserStatus userStatus;
    // 1 - cho xu ly, 2 - dang xu ly, 3 - thanh cong , 4 - Huỷ
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<OrderInfoDetailDTO> orderDetailList;
}
