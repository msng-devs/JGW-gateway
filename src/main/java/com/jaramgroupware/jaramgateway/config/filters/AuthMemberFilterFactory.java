package com.jaramgroupware.jaramgateway.config.filters;


import com.jaramgroupware.jaramgateway.service.MemberService;
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
import java.util.List;
import java.util.Objects;

/**
 * JGW-gateway 에서 인증 및 인가 부분을 담당하는 필터
 *
 * ref:
 * spring+firebase token validation https://velog.io/@couchcoding/Firebase%EB%A1%9C-Google-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-Spring-%ED%8C%8C%ED%8A%B8
 *
 */
@Component
@RequiredArgsConstructor
public class AuthMemberFilterFactory implements GatewayFilterFactory<AuthMemberFilterFactory.Config> {

    @Autowired
    private final MemberService memberService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Getter
    @Setter
    @Validated
    public static class Config {

        @NotEmpty
        private Integer role;

        @NotEmpty
        private boolean isAddUserInfo;
    }

    @Override
    public Config newConfig() {

        return new Config();
    }


    public Mono<Void> unauthorizedMessage(ServerWebExchange exchange,String message){
        logger.info("{}",message);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            String userUid = Objects.requireNonNull(request.getHeaders().get("user_uid")).get(0);

            return memberService.findMemberById(userUid)
                    .flatMap(memberDetailDto -> {
                        if (memberDetailDto.getRole().getId() < config.role)
                            return unauthorizedMessage(exchange,"(uid ="+userUid+") access. (request="+request.getURI()+")");
                        return chain.filter(exchange);
                    })
                    .switchIfEmpty(
                            unauthorizedMessage(exchange,
                                    "SECURITY_ERROR_USER_NOT_FOUND  || get (uid= "+userUid+" ) but this uid cannot found in member table! (request="+request.getURI()+")"));

        });
    }
}