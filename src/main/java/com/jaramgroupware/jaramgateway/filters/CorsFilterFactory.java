package com.jaramgroupware.jaramgateway.filters;

import com.jaramgroupware.jaramgateway.utils.ErrorResponseCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.util.Arrays;

@Slf4j
@Configuration
public class CorsFilterFactory {

    @Bean
    public GlobalFilter postGlobalFilter(){
        return (exchange, chain) -> {

            ServerHttpResponse response = exchange.getResponse();

            response.getHeaders().remove("access-control-allow-credentials");
            response.getHeaders().remove("access-control-allow-origin");
            response.getHeaders().remove("Access-Control-Request-Method");
            response.getHeaders().remove("access-control-allow-credentials");
            if(exchange.getRequest().getHeaders().getOrigin() == null){
                ErrorResponseCreator errorResponseCreator = new ErrorResponseCreator();
                errorResponseCreator.errorMessage(exchange,"Origin header not found", HttpStatus.BAD_REQUEST,"","origin 헤더가 없습니다.");
            }
            response.getHeaders().setAccessControlAllowOrigin(exchange.getRequest().getHeaders().getOrigin());
            response.getHeaders().setAccessControlAllowCredentials(true);
            response.getHeaders().setAccessControlAllowMethods(Arrays.asList(HttpMethod.GET, HttpMethod.POST,HttpMethod.DELETE,HttpMethod.PUT,HttpMethod.PATCH,HttpMethod.OPTIONS));
            response.getHeaders().setAccessControlAllowHeaders(Arrays.asList("x-requested-with", "authorization", "Content-Type", "Content-Length", "Authorization", "credential", "X-XSRF-TOKEN","set-cookie","access-control-expose-headers"));


            return chain.filter(exchange.mutate().response(response).build());
        };
    }

}
