package com.jaramgroupware.jaramgateway.domain.r2dbc.routeOption;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "ROUTE_OPTION")
public class RouteOption {

    @Id
    @Column("ROUTE_OPTION_PK")
    private Integer id;

    @Column("ROUTE_OPTION_NM")
    private String name;

}
