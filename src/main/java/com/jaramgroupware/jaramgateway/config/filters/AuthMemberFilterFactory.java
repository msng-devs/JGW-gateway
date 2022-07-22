package com.jaramgroupware.jaramgateway.config.filters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.jaramgroupware.jaramgateway.config.DefaultConfig;
import com.jaramgroupware.jaramgateway.dto.member.MemberDetailDto;
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
import java.util.NoSuchElementException;
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
    private final FirebaseAuth firebaseAuth;

    @Autowired
    private final DefaultConfig defaultConfig;

    @Autowired
    private final MemberService memberService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    public Mono unauthorizedMessage(ServerWebExchange exchange){

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            FirebaseToken decodedToken;
            Integer memberRole = defaultConfig.defaultRoleId;

            //if request has no firebase token, this filter will recognize "GUEST" role's request
            if (!request.getHeaders().containsKey("token")) {
                logger.info("Guest access {}",request.getURI());
            }
            else{
                List<String> token = request.getHeaders().get("token");
                String tokenString = Objects.requireNonNull(token).get(0);

                // verify IdToken
                // if firebase token is invalid token, reject this request (UNAUTHORIZED)
                try{
                    decodedToken = firebaseAuth.verifyIdToken(tokenString);
                } catch (FirebaseAuthException e) {
                    logger.info("SECURITY_ERROR_INVALID_TOKEN || get (token= {} ) but this is not valid token  (request={})",token,request.getURI());
                    return unauthorizedMessage(exchange);
                }

                // find member using by firebase token's uid.
                // if cannot found user, reject this request (UNAUTHORIZED)
                try{
                    MemberDetailDto member = memberService.memberDetail(decodedToken.toString());
                    memberRole = member.getRole().getId();
                } catch(NoSuchElementException e){
                    logger.info("SECURITY_ERROR_USER_NOT_FOUND  || get (uid= {} ) but this uid cannot found in member table! (request={})",decodedToken.getUid(),request.getURI());
                    return unauthorizedMessage(exchange);
                }
                logger.info("(uid ={}) access. (request={})",decodedToken.getUid(),request.getURI());
            }

            //checking role, if member's role lower then route's role, reject
            if(memberRole < config.role){
                return unauthorizedMessage(exchange);
            }

            return chain.filter(exchange);
        });
    }


}
