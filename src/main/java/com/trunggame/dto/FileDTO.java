package com.trunggame.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author congn kma
 * @since 7/12/2023
 */

@Data
@Builder
@Getter
@Setter
public class FileDTO {
    private String id;
    private String url;
}
