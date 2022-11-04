package com.jaramgroupware.jaramgateway.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaramgroupware.jaramgateway.utils.ErrorResponseCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * 자람 그룹웨어에서 유저 인가를 담당하는 필터입니다.<br><br>
 *
 * @author hrabit64(37기 황준서)
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class AuthorizationFilterFactory implements GatewayFilterFactory<AuthorizationFilterFactory.Config> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorResponseCreator errorResponseCreator;

    @Getter
    @Setter
    @Validated
    public static class Config {

        @NotEmpty
        private Integer role;

    }

    @Override
    public Config newConfig() {

        return new Config();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            String userUid = Objects.requireNonNull(request.getHeaders().get("user_pk")).get(0);
            long userRole = Long.parseLong(Objects.requireNonNull(request.getHeaders().get("role_pk")).get(0));
            logger.debug("{}",request.getHeaders().toString());
            if (userRole < config.role)
                return errorResponseCreator.errorMessage(exchange,
                        "SECURITY_ERROR_NOT_SUITABLE_ROLE",
                        HttpStatus.FORBIDDEN,
                        request.getURI().toString(),
                        "SECURITY_ERROR_NOT_SUITABLE_ROLE || (uid ="+userUid+") access. (request="+request.getURI()+")");
            logger.info("IP: {} Request: {} Uid: {} Role: {} member AuthorizationFilter.",request.getLocalAddress(),request.getURI(),userUid,userRole);
            return chain.filter(exchange.mutate().request(request).build());


        });
    }
}