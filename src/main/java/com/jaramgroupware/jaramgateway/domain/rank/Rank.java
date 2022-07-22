package com.jaramgroupware.jaramgateway.domain.rank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "RANK")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RANK_PK")
    private Integer id;

    @Column(name = "RANK_NM",nullable = false,unique = true,length = 45)
    private String name;


}
