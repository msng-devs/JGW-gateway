package com.jaramgroupware.jaramgateway.jgwauth;

import com.jaramgroupware.jaramgateway.jgwauth.exception.NotValidToken;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Setter
@RequiredArgsConstructor
@Component
public class JGWAuthClient {

    private final WebClient webClient = WebClient.create();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jgw.auth-api.url}")
    private String authApiUrl;

    public Mono<JGWAuthResult> authentication(String token){
        Mono<JGWAuthResult> result;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(authApiUrl+"/checkAccessToken")
                        .queryParam("accessToken", token)
                        .build())
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

    public Mono<JGWAuthTokenResult> tokenAuthentication(String token){

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(authApiUrl+"/checkIdToken")
                        .queryParam("idToken", token)
                        .build())
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
                .bodyToMono(JGWAuthTokenResult.class);
    }
}

