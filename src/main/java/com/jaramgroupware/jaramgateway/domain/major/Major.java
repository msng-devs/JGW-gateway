package com.jaramgroupware.jaramgateway.domain.major;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "MAJOR")
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAJOR_PK")
    private Integer id;

    @Column(name = "MAJOR_NM",nullable = false,unique = true,length = 45)
    private String name;


}
