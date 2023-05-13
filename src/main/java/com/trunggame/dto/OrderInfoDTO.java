package com.trunggame.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO {
    private List<OrderInfoDetailDTO> items;
}
