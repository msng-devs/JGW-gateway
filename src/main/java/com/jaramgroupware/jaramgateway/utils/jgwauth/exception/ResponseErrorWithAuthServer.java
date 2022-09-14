package com.jaramgroupware.jaramgateway.utils.jgwauth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseErrorWithAuthServer extends RuntimeException{
    private final String message = "인증서버 요청 중 오류가 발생했습니다.";
}
