package com.jaramgroupware.jaramgateway.domain.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_PK")
    private Integer id;

    @Column(name = "ROLE_NM",nullable = false,unique = true,length = 45)
    private String name;


}
