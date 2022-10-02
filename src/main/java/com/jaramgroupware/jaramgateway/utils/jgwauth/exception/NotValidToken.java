package com.jaramgroupware.jaramgateway.utils.jgwauth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotValidToken extends RuntimeException{
    private final String message = "valid 하지 않은 토큰입니다.";
}
