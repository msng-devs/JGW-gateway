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

    /**
     * AuthorizationFilter의 설정 클래스.
     *
     * role = 해당 path에 접근할 수 있는 최소 role
     */
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

    /**
     * AuthorizationFilter의 기능을 구현한 클래스,
     *
     *
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            String userUid = Objects.requireNonNull(request.getHeaders().get("user_uid")).get(0);
            long userRole = Long.parseLong(Objects.requireNonNull(request.getHeaders().get("user_role_id")).get(0));

            if (userRole < config.role)
                return errorResponseCreator.errorMessage(exchange,
                        "SECURITY_ERROR_NOT_SUITABLE_ROLE",
                        HttpStatus.FORBIDDEN,
                        request.getURI().toString(),
                        "SECURITY_ERROR_NOT_SUITABLE_ROLE || (uid ="+userUid+") access. (request="+request.getURI()+")");
            logger.info("IP: {} Request: {} Uid: {} member AuthorizationFilter.",request.getLocalAddress(),request.getURI(),userUid);
            return chain.filter(exchange);


        });
    }
}