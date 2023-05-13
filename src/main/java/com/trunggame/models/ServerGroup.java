package com.trunggame.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "server_group")
public class ServerGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Transient
    public String parentName;
}
