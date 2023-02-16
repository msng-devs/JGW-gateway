package com.jaramgroupware.jaramgateway.dto.role;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@Builder
public class RoleResponseDto {
    private Integer id;
    private String roleName;
}
