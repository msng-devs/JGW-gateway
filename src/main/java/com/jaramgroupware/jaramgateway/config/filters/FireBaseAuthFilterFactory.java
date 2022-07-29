package com.jaramgroupware.jaramgateway.config.filters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.jaramgroupware.jaramgateway.utils.ErrorResponseCreator;
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
public class FireBaseAuthFilterFactory implements GatewayFilterFactory<FireBaseAuthFilterFactory.Config> {

    @Autowired
    private final FirebaseAuth firebaseAuth;

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
        private boolean isEnable;
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
     * 이후에 header에 user uid를 추가함.
     *
     * @param config FireBaseAuthFilter의 설정 파일
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            FirebaseToken decodedToken;

            //if request has no firebase token, reject this request
            if (!request.getHeaders().containsKey("Authorization")||!Objects.requireNonNull(request.getHeaders().get("Authorization")).get(0).startsWith("Bearer ")) {
                return errorResponseCreator.errorMessage(exchange,
                        "SECURITY_ERROR_NO_TOKEN",
                        HttpStatus.BAD_REQUEST,
                        request.getURI().toString(),
                        "SECURITY_ERROR_NO_TOKEN || it has no token in header.  (request="+request.getURI()+")");
            }

            List<String> token = request.getHeaders().get("Authorization");
            String tokenString = Objects.requireNonNull(token).get(0).substring(7);

            // verify IdToken
            // if firebase token is invalid token, reject this request (UNAUTHORIZED)
            try {
                decodedToken = firebaseAuth.verifyIdToken(tokenString);
            } catch (FirebaseAuthException e) {
                return errorResponseCreator.errorMessage(exchange,
                        "SECURITY_ERROR_INVALID_TOKEN",
                        HttpStatus.FORBIDDEN,
                        request.getURI().toString(),
                        "SECURITY_ERROR_INVALID_TOKEN || get (token= "+token+" ) but this is not valid token  (request="+request.getURI()+")");
            }

            //add user uid in header
            request = exchange.getRequest().mutate().header("uid", decodedToken.getUid()).build();

            return chain.filter(exchange.mutate().request(request).build());
        });
    }
}
