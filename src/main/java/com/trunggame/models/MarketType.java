package com.trunggame.models;


import javax.persistence.*;

@Entity
@Table(name = "market_type")
public class MarketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",columnDefinition = "NVARCHAR2(5000)")
    private String name;

    @Column(name = "description",columnDefinition = "NVARCHAR2(5000)")
    private String description;

    public MarketType() {
    }

    public MarketType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
