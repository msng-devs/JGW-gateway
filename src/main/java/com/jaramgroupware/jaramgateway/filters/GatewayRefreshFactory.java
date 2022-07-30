package com.jaramgroupware.jaramgateway.filters;


import com.jaramgroupware.jaramgateway.utils.GatewayRefresh;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * 게이트웨이에서 게이트웨이 갱신을 담당하는 필터입니다.<br>
 * 특별한 기능 없이, 인증 및 인가처리가 모두 완료된 멤버가 해당 필터를 실행시키면 API_ROUTE 테이블을 확인하여 변경점을 반영합니다.<br>
 *
 * @author hrabit64(37기 황준서)
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class GatewayRefreshFactory implements GatewayFilterFactory<GatewayRefreshFactory.Config> {

    @Autowired
    private final GatewayRefresh gatewayRefresh;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * GatewayRefreshFilter의 설정 클래스.<br>
     * GatewayRefresh는 추가적인 설정이 없습니다.<br>
     *
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
     * GatewayRefreshFactory 의 기능을 구현한 클래스,<br>
     *
     * GatewayRefresh component 를 활용하여 게이트웨이를 갱신함<br>
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