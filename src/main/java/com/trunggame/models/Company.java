package com.trunggame.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "company")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",columnDefinition = "NVARCHAR2(2000)")
    private String name;

    @Column(name = "address",columnDefinition = "NVARCHAR2(2000)")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "website")
    private String website;

    @Column(name = "description",columnDefinition = "NVARCHAR2(2000)")
    private String description;


}
