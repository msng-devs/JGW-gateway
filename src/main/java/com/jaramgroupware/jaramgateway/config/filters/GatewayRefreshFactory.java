package com.jaramgroupware.jaramgateway.config.filters;


import com.jaramgroupware.jaramgateway.service.MemberService;
import com.jaramgroupware.jaramgateway.utils.GatewayRefresh;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * JGW-gateway 리프레쉬를 담당하는 필터
 * 해당 필터를 통하는 route를 사용하면, gateway의 route 정보를 갱신할 수 있다.
 *
 */
@Component
@RequiredArgsConstructor
public class GatewayRefreshFactory implements GatewayFilterFactory<GatewayRefreshFactory.Config> {

    @Autowired
    private final GatewayRefresh gatewayRefresh;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * AuthMemberFilter의 설정 클래스.
     *
     * role = 해당 path에 접근할 수 있는 최소 role
     * isAddUserInfo = request의 body에 member를 추가할껀지 여부
     */
    @Getter
    @Setter
    @Validated
    public static class Config {
        @NotEmpty
        private boolean isEnable;
    }

    @Override
    public Config newConfig() {

        return new Config();
    }


    /**
     * GatewayRefreshFactory 의 기능을 구현한 클래스,
     *
     * GatewayRefresh component 를 활용하여 게이트웨이를 갱신함
     *
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            gatewayRefresh.refreshRoutes();
            logger.info("IP: {} Uid: {} run refresh.",exchange.getRequest().getLocalAddress(),exchange.getRequest().getHeaders().get("user_uid"));
            return chain.filter(exchange);

        });
    }
}