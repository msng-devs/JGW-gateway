package com.jaramgroupware.jaramgateway.domain.r2dbc.method;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "METHOD")
public class Method {

    @Id
    @Column("METHOD_PK")
    private Integer id;

    @Column("METHOD_NM")
    private String name;
}
