package com.jaramgroupware.jaramgateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Configuration
public class CorsFilterFactory {

    @Value("${jgw.cors.origin}")
    private String origin;

    @Bean
    public GlobalFilter postGlobalFilter(){
        return (exchange, chain) -> {

            ServerHttpResponse response = exchange.getResponse();

            response.getHeaders().remove("access-control-allow-credentials");
            response.getHeaders().remove("access-control-allow-origin");
            response.getHeaders().remove("Access-Control-Request-Method");
            response.getHeaders().remove("access-control-allow-credentials");

            response.getHeaders().setAccessControlAllowOrigin((Objects.requireNonNull(exchange.getRequest().getHeaders().getOrigin()).isEmpty()) ? origin : exchange.getRequest().getHeaders().getOrigin());
            response.getHeaders().setAccessControlAllowCredentials(true);
            response.getHeaders().setAccessControlAllowMethods(Arrays.asList(HttpMethod.GET, HttpMethod.POST,HttpMethod.DELETE,HttpMethod.PUT,HttpMethod.PATCH,HttpMethod.OPTIONS));
            response.getHeaders().setAccessControlAllowHeaders(Arrays.asList("x-requested-with", "authorization", "Content-Type", "Content-Length", "Authorization", "credential", "X-XSRF-TOKEN"));


            return chain.filter(exchange.mutate().response(response).build());
        };
    }

}
