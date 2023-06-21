package com.trunggame.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author vula
 * @since 7/12/2023
 */

@Data
@Builder
@Getter
@Setter
public class ValidateRequestDTO {

    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 15)
    private String phoneNumber;

    private String username;

}
