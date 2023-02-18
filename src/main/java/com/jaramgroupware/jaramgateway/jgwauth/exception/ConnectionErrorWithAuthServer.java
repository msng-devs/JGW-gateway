package com.jaramgroupware.jaramgateway.jgwauth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ConnectionErrorWithAuthServer extends RuntimeException{
    private final String message = "인증서버 연결 중 오류가 발생했습니다.";
}
