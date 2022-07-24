package com.jaramgroupware.jaramgateway.domain.service;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "SERVICE")
public class Service {

    @Id
    @Column("SERVICE_PK")
    private Integer id;

    @Column("SERVICE_NM")
    private String name;

    @Column("SERVICE_DOMAIN")
    private String domain;

    @Column("SERVICE_INDEX")
    private String index;
}
