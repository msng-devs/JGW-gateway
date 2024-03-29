package com.jaramgroupware.jaramgateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class CorsConfig {

    private final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Content-Length, Authorization, credential, X-XSRF-TOKEN,set-cookie,access-control-expose-headers";
    private final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS, PATCH";

    private final String MAX_AGE = "7200"; //2 hours (2 * 60 * 60)

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();

            if (CorsUtils.isCorsRequest(request)) {

                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();

                if (request.getHeaders().getOrigin() == null) {
                    response.setStatusCode(HttpStatus.BAD_REQUEST);
                    return Mono.empty();
                }

                headers.add("Access-Control-Allow-Origin", request.getHeaders().getOrigin());
                headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
                headers.add("Access-Control-Allow-Credentials", "true");
                headers.add("Access-Control-Max-Age", MAX_AGE);
                headers.add("Access-Control-Allow-Headers",ALLOWED_HEADERS);


                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }

}