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

        //IETF 표준
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
