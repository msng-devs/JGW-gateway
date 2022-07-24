package com.jaramgroupware.jaramgateway.domain.rank;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "RANK")
public class Rank {

    @Id
    @Column("RANK_PK")
    private Integer id;

    @Column("RANK_NM")
    private String name;


}
