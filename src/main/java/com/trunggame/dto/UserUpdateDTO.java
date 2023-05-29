package com.trunggame.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO implements Serializable {
    private Long id;
    private String phoneNumber;
    private String address;
    private  String fullName;
    private  String nickname;
}
