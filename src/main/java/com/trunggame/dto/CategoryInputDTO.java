package com.trunggame.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Getter
@Setter
public class CategoryInputDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private  Long parentCategoryId;
}
