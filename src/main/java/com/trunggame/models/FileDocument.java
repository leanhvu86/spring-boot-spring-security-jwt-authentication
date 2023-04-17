package com.trunggame.models;

import com.trunggame.enums.EFileDocumentStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "file")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 255)
    private String name;


    @NonNull
    @Column(nullable = false, length = 255)
    private String uniqId;

    @NonNull
    @Column(nullable = false, length = 255)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EFileDocumentStatus status;

    @Column(length = 20)
    private String fileType;

    @Column(nullable = false, length = 255)
    private String previewUrl;

    @Column( name = "owner_id")
    private Long ownerId;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
