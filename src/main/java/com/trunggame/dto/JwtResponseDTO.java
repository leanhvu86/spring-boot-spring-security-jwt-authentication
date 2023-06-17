package com.trunggame.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @author congn kma
 * @since 7/12/2023
 */
@Data
@Getter
@Setter
public class JwtResponseDTO {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String email;
  private String fullName;
  private String nickName;
  private String address;
  private String phoneNumber;
  private List<String> roles;

  public JwtResponseDTO(String accessToken, Long id, String nickName, String email, List<String> roles, String address
  , String fullName, String phoneNumber) {
    this.token = accessToken;
    this.id = id;
    this.nickName = nickName;
    this.email = email;
    this.roles = roles;
    this.address = address;
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
  }
}
