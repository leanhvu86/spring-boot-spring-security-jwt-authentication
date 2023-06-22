package com.trunggame.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Builder
@Getter
@Setter
public class BannerDTO {
    private Long id;

    private String status;

    private String fileId;

    private String imageUrl;
    private String priority;

}

