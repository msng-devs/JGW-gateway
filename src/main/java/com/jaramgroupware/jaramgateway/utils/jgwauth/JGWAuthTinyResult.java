package com.jaramgroupware.jaramgateway.utils.jgwauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JGWAuthTinyResult {

    private boolean valid;
    private String uid;

}
