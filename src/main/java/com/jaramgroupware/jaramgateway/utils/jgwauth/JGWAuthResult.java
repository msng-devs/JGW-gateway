package com.jaramgroupware.jaramgateway.utils.jgwauth;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class JGWAuthResult {

    private HttpStatus httpStatus;
    private boolean isValid;
    private String uid;
    private String roleName;
    private Long roleID;

}
