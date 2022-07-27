package com.jaramgroupware.jaramgateway.config.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.jaramgroupware.jaramgateway.service.MemberService;
import io.grpc.internal.JsonParser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * JGW-gateway 인가 부분을 담당하는 필터
 *
 *
 */
@Component
@RequiredArgsConstructor
public class AuthMemberFilterFactory implements GatewayFilterFactory<AuthMemberFilterFactory.Config> {

    @Autowired
    private final MemberService memberService;
    @Autowired
    private final ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;
    private final ObjectMapper mapper = new ObjectMapper();
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
        private Integer role;

        @NotEmpty
        private boolean isAddUserInfo;
    }

    @Override
    public Config newConfig() {

        return new Config();
    }

    /**
     * 인증오류 발생시 해당 요청을 reject 하고, 401 전달하는 클래스
     * @param exchange ServerWebExchange
     * @param message 로그에 남길 메시지
     * @return
     */
    public Mono<Void> unauthorizedMessage(ServerWebExchange exchange,String message){
        logger.info("{}",message);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    /**
     * 인증오류 발생시 해당 요청을 reject 하고, 401 전달하는 클래스
     * @param exchange ServerWebExchange
     * @param message 로그에 남길 메시지
     * @return
     */
    public Mono<Void> errorMessage(ServerWebExchange exchange,String message){
        logger.info("{}",message);
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return exchange.getResponse().setComplete();
    }

    /**
     * AuthMemberFilter의 기능을 구현한 클래스,
     *
     * AuthMemberFilter는 아래와 같이 동작함.
     *
     * 1. header의 사용자 uid로 member table에서 해당 멤버를 조회, 만약 발견이 되지 않으면 인증오류 발생
     * 2. 해당 member의 role을 조회하여 설정의 권한에 적절하지 않은 role이라면 인증오류 발생
     *
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            String userUid = Objects.requireNonNull(request.getHeaders().get("user_uid")).get(0);
            String requestBody = exchange.getAttribute("cachedRequestBodyObject");

            return memberService.findMemberById(userUid)
                    .flatMap(memberDetailDto -> {
                        //if not register member, reject
                        if (memberDetailDto == null)
                            return unauthorizedMessage(exchange,
                                    "SECURITY_ERROR_USER_NOT_FOUND  || get (uid= "+userUid+" ) but this uid cannot found in member table! (request="+request.getURI()+")");

                        //if member's role is lower than route's role, reject
                        if (memberDetailDto.getRole().getId() < config.role)
                            return unauthorizedMessage(exchange,"SECURITY_ERROR_NOT_SUITABLE_ROLE || (uid ="+userUid+") access. (request="+request.getURI()+")");

                        if (config.isAddUserInfo){

                            //parse request body
                            JSONObject body;
                            if(requestBody != null) body = new JSONObject(requestBody);

                            //if it hasn't body, create empty json
                            else body = new JSONObject();

                            //put member json obeject.
                            body.put("member",new JSONObject(memberDetailDto));

                            logger.debug(body.toString());
                            //modifyRequestBody, and return it
                            ModifyRequestBodyGatewayFilterFactory.Config modifyRequestConfig = new ModifyRequestBodyGatewayFilterFactory.Config()
                                    .setContentType(ContentType.APPLICATION_JSON.getMimeType())
                                    .setRewriteFunction(String.class, String.class, (newExchange, originalRequestBody) -> Mono.just(body.toString()));

                            return modifyRequestBodyGatewayFilterFactory.apply(modifyRequestConfig).filter(exchange, chain);
                        }

                        return chain.filter(exchange);
                    });

        });
    }
}