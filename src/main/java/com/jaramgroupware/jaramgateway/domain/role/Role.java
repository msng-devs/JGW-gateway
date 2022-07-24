package com.jaramgroupware.jaramgateway.domain.role;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "ROLE")
public class Role {

    @Id
    @Column("ROLE_PK")
    private Integer id;

    @Column("ROLE_NM")
    private String name;


}
