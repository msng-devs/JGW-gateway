package com.jaramgroupware.jaramgateway.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaramgroupware.jaramgateway.dto.member.MemberDetailDto;
import com.jaramgroupware.jaramgateway.service.MemberService;
import com.jaramgroupware.jaramgateway.utils.ErrorResponseCreator;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * 자람 그룹웨어에서 유저 인증 및 인가를 담당하는 필터입니다.<br><br>
 *
 * <img src="https://github.com/msng-devs/JGW-Docs/blob/main/images/%ED%95%84%ED%84%B0%EA%B5%AC%EC%A1%B0.png?raw=true"><br>
 *
 * @author hrabit64(37기 황준서)
 * @version 1.0
 * @since 1.0
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
    private final ErrorResponseCreator errorResponseCreator;

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
     * 기존 request body에 json object로 멤버 정보를 추가합니다.
     *
     * @param memberDetailDto 추가할 멤버의 정보
     * @param originalRequestBody 기존 request body
     * @return
     */
    public Mono<String> rewriteBody(MemberDetailDto memberDetailDto,String originalRequestBody){
        //parse request body
        JSONObject newBody;
        if(originalRequestBody != null) newBody = new JSONObject(originalRequestBody);

        //if it hasn't body, create empty json
        else newBody = new JSONObject();

        //put member json obeject.
        newBody.put("member",new JSONObject(memberDetailDto));

        return Mono.just(newBody.toString());
    }
    /**
     * AuthMemberFilter의 기능을 구현한 클래스,
     *
     * AuthMemberFilter는 아래와 같이 동작함.
     *
     * 1. header의 사용자 uid로 member table에서 해당 멤버를 조회, 만약 발견이 되지 않으면 인증오류 발생
     * 2. 해당 member의 role을 조회하여 설정의 권한에 적절하지 않은 role이라면 인증오류 발생
     * 3. 만약 isAddUserInfo이 활성화 상태라면, request body에 멤버 정보 추가
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            String userUid = Objects.requireNonNull(request.getHeaders().get("uid")).get(0);

            return memberService.findMemberById(userUid)
                    .flatMap(memberDetailDto -> {
                        //if not register member, reject
                        if (memberDetailDto == null)
                            return errorResponseCreator.errorMessage(exchange,
                                            "SECURITY_ERROR_USER_NOT_FOUND",
                                            HttpStatus.FORBIDDEN,
                                            request.getURI().toString(),
                                            "SECURITY_ERROR_USER_NOT_FOUND  || get (uid= "+userUid+" ) but this uid cannot found in member table! (request="+request.getURI()+")");

                        //if member's role is lower than route's role, reject
                        if (memberDetailDto.getRole().getId() < config.role)
                            return errorResponseCreator.errorMessage(exchange,
                                    "SECURITY_ERROR_NOT_SUITABLE_ROLE",
                                    HttpStatus.FORBIDDEN,
                                    request.getURI().toString(),
                                    "SECURITY_ERROR_NOT_SUITABLE_ROLE || (uid ="+userUid+") access. (request="+request.getURI()+")");

                        if (config.isAddUserInfo){

                            //modifyRequestBody, and return it
                            ModifyRequestBodyGatewayFilterFactory.Config modifyRequestConfig = new ModifyRequestBodyGatewayFilterFactory.Config()
                                    .setContentType(ContentType.APPLICATION_JSON.getMimeType())
                                    .setRewriteFunction(String.class, String.class, (newExchange, originalRequestBody) -> rewriteBody(memberDetailDto,originalRequestBody));
                            return modifyRequestBodyGatewayFilterFactory.apply(modifyRequestConfig).filter(exchange, chain);
                        }
                        logger.info("IP: {} Request: {} Uid: {} member auth pass.",request.getLocalAddress(),request.getURI(),memberDetailDto.getId());
                        return chain.filter(exchange);
                    });

        });
    }
}