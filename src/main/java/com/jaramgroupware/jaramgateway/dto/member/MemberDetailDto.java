package com.jaramgroupware.jaramgateway.dto.member;

import com.jaramgroupware.jaramgateway.domain.major.Major;
import com.jaramgroupware.jaramgateway.domain.member.Member;
import com.jaramgroupware.jaramgateway.domain.rank.Rank;
import com.jaramgroupware.jaramgateway.domain.role.Role;
import lombok.*;

@Data
@ToString
@Getter
@NoArgsConstructor
public class MemberDetailDto {

    private String id;
    private String email;
    private String name;
    private String phoneNumber;
    private String studentID;
    private Major major;
    private Rank rank;
    private Role role;
    private Integer year;


    public MemberDetailDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.phoneNumber = member.getPhoneNumber();
        this.studentID = member.getStudentID();
        this.major = member.getMajor();
        this.rank = member.getRank();
        this.role = member.getRole();
        this.year = member.getYear();
    }
}
