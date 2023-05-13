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

    @Column(name = "title",columnDefinition = "NVARCHAR2(5000)")
    private String title;

    @Column(name = "contentEN")
    private String contentEN;

    @Column(name = "contentVI",columnDefinition = "NVARCHAR2(5000)")
    private String contentVI;

    @Column(name = "imageId")
    private String imageId;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "link")
    private String link;

    @Column(name = "author",columnDefinition = "NVARCHAR2(5000)")
    private String author;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date postDate;
}
