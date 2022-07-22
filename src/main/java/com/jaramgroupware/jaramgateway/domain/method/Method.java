package com.jaramgroupware.jaramgateway.domain.method;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "METHOD")
public class Method {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "METHOD_PK")
    private Integer id;

    @Column(name = "METHOD_NM",nullable = false,unique = true,length = 45)
    private String name;
}
