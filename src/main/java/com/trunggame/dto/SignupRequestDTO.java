package com.trunggame.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;

/**
 * @author congn kma
 * @since 7/12/2023
 */

@Data
@Builder
@Getter
@Setter
public class SignupRequestDTO {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @Size(max = 15)
  private String phoneNumber;

  @Size(max = 150)
  private String fullName;


  @Size(max = 150)
  private String address;

  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;
}
