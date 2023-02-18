package com.jaramgroupware.jaramgateway.dto.service;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ServiceInfoResponseDto {
    private Integer id;
    private String name;
    private String index;
    private String domain;
}
