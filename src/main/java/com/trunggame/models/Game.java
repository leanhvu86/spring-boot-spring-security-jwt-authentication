package com.trunggame.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "game")
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR CHARACTER SET utf8")
    private String description;

    @Column(name = "description_en", columnDefinition = "VARCHAR CHARACTER SET utf8")
    private String descriptionEn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Column(nullable = false)
    private String type;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "image_id")
    private String imageId;


    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "youtubeLink")
    private String youtubeLink;

    @Column(name = "content_vi", columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String contentVi;

    @Column(name = "content_en", columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String contentEn;

    @Column(name = "marketType")
    private String marketType;

    @Column(name = "companyName", columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String companyName;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Transient
    private String previewUrl;


    public enum Status {
        ACTIVE,
        INACTIVE
    }
}