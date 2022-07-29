package com.jaramgroupware.jaramgateway.domain.Error;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "ERROR")
public class ErrorInfo {

    @Id
    @Column("ERROR_PK")
    private Integer id;

    @Column("ERROR_NM")
    private String name;

    @Column("ERROR_TITLE")
    private String title;

    @Column("ERROR_HTTP_CODE")
    private String code;

    @Column("ERROR_INDEX")
    private String index;

}
