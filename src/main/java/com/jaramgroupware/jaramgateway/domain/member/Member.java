package com.jaramgroupware.jaramgateway.domain.member;

import com.jaramgroupware.jaramgateway.domain.major.Major;
import com.jaramgroupware.jaramgateway.domain.rank.Rank;
import com.jaramgroupware.jaramgateway.domain.role.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column("MEMBER_PK")
    private String id;

    @Email
    @Column("MEMBER_EMAIL")
    private String email;

    @Column("MEMBER_NM")
    private String name;

    @Column("MEMBER_CELL_PHONE_NUMBER")
    private String phoneNumber;

    @Column("MEMBER_STUDENT_ID")
    private String studentID;

    @Column("MEMBER_MODIFIED_DTTM")
    private LocalDateTime modifiedDateTime;

    @Column("MEMBER_CREATED_DTTM")
    private LocalDateTime createdDateTime;

    @Column("MEMBER_CREATED_BY")
    private String createdBy;

    @Column("MEMBER_MODIFIED_BY")
    private String modifiedBy;

    @Transient
    private Major major;

    @Transient
    private Rank rank;

    @Transient
    private Role role;

    @Column("MEMBER_YEAR")
    private Integer year;

    @Column("MEMBER_LEAVE_ABSENCE")
    private Byte isLeaveAbsence;

}
