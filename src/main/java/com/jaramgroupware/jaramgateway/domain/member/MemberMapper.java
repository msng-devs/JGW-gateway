package com.jaramgroupware.jaramgateway.domain.member;

import com.jaramgroupware.jaramgateway.domain.apiRoute.ApiRoute;
import com.jaramgroupware.jaramgateway.domain.major.Major;
import com.jaramgroupware.jaramgateway.domain.rank.Rank;
import com.jaramgroupware.jaramgateway.domain.role.Role;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.BiFunction;

@Component
public class MemberMapper implements BiFunction<Row, RowMetadata, Member> {

    @Override
    public Member apply(Row row, RowMetadata rowMetadata) {

        return Member.builder()
                .id(row.get("MEMBER_PK",String.class))
                .email(row.get("MEMBER_EMAIL",String.class))
                .name(row.get("MEMBER_NM",String.class))
                .phoneNumber(row.get("MEMBER_CELL_PHONE_NUMBER",String.class))
                .studentID(row.get("MEMBER_STUDENT_ID",String.class))
                .modifiedDateTime(row.get("MEMBER_MODIFIED_DTTM", LocalDateTime.class))
                .createdDateTime(row.get("MEMBER_CREATED_DTTM", LocalDateTime.class))
                .isLeaveAbsence(row.get("MEMBER_LEAVE_ABSENCE",Byte.class))
                .major(
                        Major.builder()
                                .id(row.get("MAJOR_PK",Integer.class))
                                .name(row.get("MAJOR_NM",String.class))
                                .build())
                .rank(
                        Rank.builder()
                                .id(row.get("RANK_PK",Integer.class))
                                .name(row.get("RANK_NM",String.class))
                                .build())
                .role(
                        Role.builder()
                                .id(row.get("ROLE_PK",Integer.class))
                                .name(row.get("ROLE_NM",String.class))
                                .build())
                .year(row.get("MEMBER_YEAR",Integer.class))
                .modifiedBy(row.get("MEMBER_MODIFIED_BY",String.class))
                .createdBy(row.get("MEMBER_CREATED_BY",String.class))
                .dateofbirth(row.get("MEMBER_DATEOFBIRTH", LocalDate.class))
                .build();

    }
}
