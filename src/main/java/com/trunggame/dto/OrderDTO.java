package com.trunggame.dto;

import com.trunggame.models.*;
import lombok.*;

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
    private List<OrderInfoDetailDTO> orderDetailList;
}
