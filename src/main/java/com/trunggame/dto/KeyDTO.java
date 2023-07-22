package com.trunggame.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Builder
@Getter
@Setter
public class KeyDTO {

    private String publicKey;

    private String privateKey;

    private String encryptString;

}

