package com.jaramgroupware.jaramgateway.dto.member;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.jaramgateway.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String id;
    private String email;
    private String name;
    private String phoneNumber;
    private String studentID;
    private String major;
    private String rank;
    private String role;
    private Integer year;

    @Builder
    public MemberResponseDto(String id, String email, String name, String phoneNumber, String studentID, String major, String rank, String role, Integer year) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.studentID = studentID;
        this.major = major;
        this.rank = rank;
        this.role = role;
        this.year = year;
    }

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.phoneNumber = member.getPhoneNumber();
        this.studentID = member.getStudentID();
        this.major = member.getMajor().getName();
        this.rank = member.getRank().getName();
        this.role = member.getRole().getName();
        this.year = member.getYear();
    }
}
