package com.jaramgroupware.jaramgateway.jgwauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JGWAuthTokenResult {

    private boolean isValid;
    private String uid;

}
