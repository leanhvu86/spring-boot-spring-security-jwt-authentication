package com.trunggame.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String title;
    private String contentEN;
    private String contentVI;
    private String imageId;
    private String imageUrl;
    private String link;
    private String author;
}
