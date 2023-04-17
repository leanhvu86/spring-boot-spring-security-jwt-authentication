package com.trunggame.dto;


import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDTO<T> {
    private String message;
    private Integer status;
    private Integer soarCode;
    private  T data;
}
