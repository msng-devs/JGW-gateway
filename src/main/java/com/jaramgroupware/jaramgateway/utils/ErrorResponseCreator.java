package com.jaramgroupware.jaramgateway.utils;

import com.jaramgroupware.jaramgateway.domain.Error.ErrorInfo;
import com.jaramgroupware.jaramgateway.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 필터에서 사용가능한 에러 response을 생성 및 반환하는 클래스
 * 전달 받은 내용을 로그로 남기고,
 * IETF 표준을 준수하여 에러 response를 생성함.
 * ERROR 테이블에 저장되어있는 오류코드에 대한 정보를 ErrorService를 통해 찾고, 해당 정보로 response를 생성.
 * @author hrabit64(37기 황준서)
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ErrorResponseCreator {

    private final ErrorService errorService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    //TODO HTTP code DB 연동으로 수정하기
    public Mono<Void> errorMessage(ServerWebExchange exchange,String errorName,HttpStatus status,String instance,String logMessage){

        logger.debug(logMessage);
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.setStatusCode(status);
        serverHttpResponse.getHeaders().add("Content-Type","application/json");

        return errorService.findErrorByName(errorName)
                .flatMap(errorInfo -> {
                    String errorMessage = "{\"status\":\""+status+"\","
                            +"\"type\":\""+errorInfo.getId()+"\","
                            +"\"title\":\""+errorInfo.getName()+"\","
                            +"\"detail \":\""+errorInfo.getIndex()+"\","
                            +"\"instance \":\""+instance +"\"}";

                    byte[] response =errorMessage.getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(response);
                    return exchange.getResponse().writeWith(Flux.just(buffer));
                });

    }
}
