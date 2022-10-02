package com.jaramgroupware.jaramgateway.filters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.jaramgroupware.jaramgateway.utils.ErrorResponseCreator;
import com.jaramgroupware.jaramgateway.utils.jgwauth.JGWAuthClient;
import com.jaramgroupware.jaramgateway.utils.jgwauth.JGWAuthResult;
import com.jaramgroupware.jaramgateway.utils.jgwauth.JGWAuthTinyResult;
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
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

/**
 * 자람 그룹웨어에서 유저 인증을 담당하는 필터입니다.<br>
 *
 * <img src="https://github.com/msng-devs/JGW-Docs/blob/main/images/%ED%95%84%ED%84%B0%EA%B5%AC%EC%A1%B0.png?raw=true"><br>
 *
 * @author hrabit64(37기 황준서)
 * @version 1.0
 * @since 1.0
 */

@Component
@RequiredArgsConstructor
public class AuthenticationFilterFactory implements GatewayFilterFactory<AuthenticationFilterFactory.Config> {

    private final JGWAuthClient jgwAuthClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorResponseCreator errorResponseCreator;

    /**
     * FireBaseAuthFilter의 설정 클래스.
     * FireBaseAuthFilter는 별도의 설정이 없음.
     */
    @Getter
    @Setter
    @Validated
    public static class Config {
        @NotEmpty
        private boolean isOnlyToken;
    }

    @Override
    public Config newConfig() {

        return new Config();
    }



    /**
     * FireBaseAuthFilter 구현 클래스
     * FireBaseAuthFilter는 아래와 같은 순서로 동작함.
     *
     * 1. header의 token을 가져옴 만약 token이 없다면, 인증 오류를 리턴함
     * 2. 해당 토큰을 firebase admin sdk를 사용하여 인증 만약 인증에 실패하면, 인증 오류를 리턴함
     *
     * 이후 "user_uid" header에 user uid를 추가함.
     *
     * @param config FireBaseAuthFilter의 설정 파일
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            //if request has no firebase token, reject this request
            if (!request.getHeaders().containsKey("Authorization")||!Objects.requireNonNull(request.getHeaders().get("Authorization")).get(0).startsWith("Bearer ")) {
                return errorResponseCreator.errorMessage(exchange,
                        "SECURITY_ERROR_NO_TOKEN",
                        HttpStatus.BAD_REQUEST,
                        request.getURI().toString(),
                        "SECURITY_ERROR_NO_TOKEN || it has no token in header.  (request="+request.getURI()+")");
            }

            //get firebase token
            List<String> token = request.getHeaders().get("Authorization");
            String tokenString = Objects.requireNonNull(token).get(0).substring(7);

            //get authResult
            if(!config.isOnlyToken){
                Mono<JGWAuthResult> authResult = jgwAuthClient.authentication(tokenString);

                return authResult.flatMap(
                        jgwAuthResult -> {
                            logger.debug("res {}",jgwAuthResult.toString());
                            //if not valid, reject
                            if(!jgwAuthResult.isValid())
                                return errorResponseCreator.errorMessage(exchange,
                                        "SECURITY_ERROR_INVALID_TOKEN",
                                        HttpStatus.FORBIDDEN,
                                        request.getURI().toString(),
                                        "SECURITY_ERROR_INVALID_TOKEN || get (token= "+token+" ) but this is not valid token  (request="+request.getURI()+")");

                            ServerHttpRequest newRequest = exchange.getRequest();
                            newRequest = exchange.getRequest()
                                    .mutate()
                                    .header("user_pk", jgwAuthResult.getUid())
                                    .header("role_pk", jgwAuthResult.getRoleID().toString())
                                    .build();

                            logger.info("IP: {} Request: {} Token: {} AuthenticationFilter pass",request.getLocalAddress(),request.getURI(),token);
                            return chain.filter(exchange.mutate().request(newRequest).build());
                        }
                );
            }
            else{
                Mono<JGWAuthTinyResult> authResult = jgwAuthClient.tokenAuthentication(tokenString);

                return authResult.flatMap(
                        jgwAuthResult -> {
                            //if not valid, reject
                            if(!jgwAuthResult.isValid())
                                return errorResponseCreator.errorMessage(exchange,
                                        "SECURITY_ERROR_INVALID_TOKEN",
                                        HttpStatus.FORBIDDEN,
                                        request.getURI().toString(),
                                        "SECURITY_ERROR_INVALID_TOKEN || get (token= "+token+" ) but this is not valid token  (request="+request.getURI()+")");

                            ServerHttpRequest newRequest = exchange.getRequest()
                                    .mutate()
                                    .header("user_pk", jgwAuthResult.getUid())
                                    .build();

                            logger.info("IP: {} Request: {} Token: {} AuthenticationFilter pass",request.getLocalAddress(),request.getURI(),token);
                            return chain.filter(exchange.mutate().request(newRequest).build());
                        }
                );
            }


        });
    }
}
