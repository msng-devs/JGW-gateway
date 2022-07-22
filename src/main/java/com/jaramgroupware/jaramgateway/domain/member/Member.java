package com.jaramgroupware.jaramgateway.domain.member;

import com.jaramgroupware.jaramgateway.domain.major.Major;
import com.jaramgroupware.jaramgateway.domain.rank.Rank;
import com.jaramgroupware.jaramgateway.domain.role.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "MEMBER_PK",length = 28)
    private String id;

    @Email
    @Column(name = "MEMBER_EMAIL",nullable = false,length =45)
    private String email;

    @Column(name = "MEMBER_NM",nullable = false,length =45)
    private String name;

    @Column(name="MEMBER_CELL_PHONE_NUMBER",length =15)
    private String phoneNumber;

    @Column(name= "MEMBER_STUDENT_ID",nullable = false,unique = true,length =45)
    private String studentID;

    @ManyToOne
    @JoinColumn(name = "MAJOR_MAJOR_PK",nullable = false)
    private Major major;

    @ManyToOne
    @JoinColumn(name = "RANK_RANK_PK",nullable = false)
    private Rank rank;

    @ManyToOne
    @JoinColumn(name = "ROLE_ROLE_PK",nullable = false)
    private Role role;

    @Column(name="MEMBER_YEAR",nullable = false)
    private Integer year;

    @Builder
    public Member(String id,
                  @Email String email, String name, String phoneNumber, Major major,
                  String studentID, Rank rank, Role role, Integer year) {

        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.studentID = studentID;
        this.rank = rank;
        this.role = role;
        this.year = year;
        this.major = major;
    }
}
