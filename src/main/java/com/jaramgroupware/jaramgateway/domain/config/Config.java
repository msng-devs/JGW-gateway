package com.jaramgroupware.jaramgateway.domain.config;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "CONFIG")
public class Config {

    @Id
    @Column("CONFIG_PK")
    private Integer id;

    @Column("CONFIG_NM")
    private String name;

    @Column("CONFIG_VAL")
    private String val;

}
