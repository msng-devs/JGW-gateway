package com.jaramgroupware.jaramgateway.utils.jgwauth;

import com.jaramgroupware.jaramgateway.utils.jgwauth.exception.ConnectionErrorWithAuthServer;
import com.jaramgroupware.jaramgateway.utils.jgwauth.exception.ResponseErrorWithAuthServer;
import io.netty.channel.ConnectTimeoutException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class JGWAuthClient {

    private final WebClient webClient = WebClient.create();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jgw.auth-api.url}")
    private String authApiUrl;

    public Mono<JGWAuthResult> authentication(String token){
        
        return webClient.get()
                .uri(authApiUrl+token)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> {
                            logger.info("JGWAuthServer 연결중 오류가 발생했습니다.");
                            return response.bodyToMono(String.class).map(Exception::new);
                        }
                )
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> {
                            logger.warn("JGWAuthServer 요청 형식이 잘못되었습니다. 코드를 확인해주세요");
                            return response.bodyToMono(String.class).map(Exception::new);
                        }
                )
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        response -> {
                            logger.warn("JGWAuthServer 요청 형식이 잘못되었습니다. 코드를 확인해주세요");
                            return response.bodyToMono(String.class).map(Exception::new);
                        }
                )
                .bodyToMono(JGWAuthResult.class);
    }
}

