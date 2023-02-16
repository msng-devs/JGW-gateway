package com.jaramgroupware.jaramgateway.dto.method;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@Builder
public class MethodResponseDto {
    private Integer id;
    private String methodName;
}
