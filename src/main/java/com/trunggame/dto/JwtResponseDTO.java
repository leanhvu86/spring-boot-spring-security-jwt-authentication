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
  private String username;
  private String email;
  private String fullName;
  private String address;
  private String phoneNumber;
  private List<String> roles;

  public JwtResponseDTO(String accessToken, Long id, String username, String email, List<String> roles, String address
  , String fullName, String phoneNumber) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
    this.address = address;
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
  }
}
