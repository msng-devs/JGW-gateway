package com.jaramgroupware.jaramgateway.domain.major;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "MAJOR")
public class Major {

    @Id
    @Column("MAJOR_PK")
    private Integer id;

    @Column("MAJOR_NM")
    private String name;


}
