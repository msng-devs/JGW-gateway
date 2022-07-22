package com.jaramgroupware.jaramgateway.domain.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "SERVICE")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVICE_PK")
    private Integer id;

    @Column(name = "SERVICE_NM",nullable = false,unique = true,length = 45)
    private String name;

    @Column(name = "SERVICE_DOMAIN",nullable = false,unique = true,length = 45)
    private String domain;

    @Column(name = "SERVICE_INDEX")
    private String index;
}
