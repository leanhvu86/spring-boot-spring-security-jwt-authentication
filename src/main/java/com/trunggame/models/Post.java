package com.trunggame.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post")
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",length = 10000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String title;

    @Column(name = "contentEN")
    private String contentEN;

    @Column(name = "contentVI",length = 10000, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String contentVI;

    @Column(name = "imageId")
    private String imageId;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "link")
    private String link;

    @Column(name = "author",columnDefinition = "VARCHAR(5000) CHARACTER SET utf8")
    private String author;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date postDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Post.Status status = Post.Status.ACTIVE;

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
