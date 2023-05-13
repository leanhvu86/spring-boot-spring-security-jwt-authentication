package com.trunggame.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`smart_tag_game`")
@Entity
public class SmartTagGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "smart_tag_id", nullable = false)
    private Long smartTagId;

    @Column(name = "game_id", nullable = false)
    private Long gameId;

}
