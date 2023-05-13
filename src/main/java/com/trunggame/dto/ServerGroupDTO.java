package com.trunggame.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerGroupDTO {
    private Long id;
    private String name;
    private Long parentId;
    private String parentName;
}
