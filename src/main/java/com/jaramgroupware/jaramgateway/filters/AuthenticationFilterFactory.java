package com.jaramgroupware.jaramgateway.filters;

import com.jaramgroupware.jaramgateway.utils.ErrorResponseCreator;
import com.jaramgroupware.jaramgateway.jgwauth.JGWAuthClient;
import com.jaramgroupware.jaramgateway.jgwauth.JGWAuthResult;
import com.jaramgroupware.jaramgateway.jgwauth.JGWAuthTokenResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Component
@RequiredArgsConstructor
public class AuthenticationFilterFactory implements GatewayFilterFactory<AuthenticationFilterFactory.Config> {

    private final JGWAuthClient jgwAuthClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorResponseCreator errorResponseCreator;

    @Getter
    @Setter
    @Validated
    public static class Config {
        @NotEmpty
        private boolean isOnlyToken;

        @NotEmpty
        private boolean isOptional;
    }

    @Override
    public Config newConfig() {

        return new Config();
    }

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
                            //if not isValid, reject
                            if(!jgwAuthResult.isValid() && !config.isOptional())
                                return errorResponseCreator.errorMessage(exchange,
                                        "SECURITY_ERROR_INVALID_TOKEN",
                                        HttpStatus.FORBIDDEN,
                                        request.getURI().toString(),
                                        "SECURITY_ERROR_INVALID_TOKEN || get (token= "+token+" ) but this is not isValid token  (request="+request.getURI()+")");
                            //if optional,
                            else if(!jgwAuthResult.isValid() && config.isOptional()){
                                ServerHttpRequest newRequest = exchange.getRequest()
                                        .mutate()
                                        .header("user_pk", "null")
                                        .header("role_pk", "null")
                                        .build();

                                logger.info("IP: {} Request: {} Token: {} AuthenticationFilter Fail, But isOptional Option is enable, so return null user_pk, null role_pk",request.getLocalAddress(),request.getURI(),token);
                                return chain.filter(exchange.mutate().request(newRequest).build());
                            }

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
                Mono<JGWAuthTokenResult> authResult = jgwAuthClient.tokenAuthentication(tokenString);

                return authResult.flatMap(
                        jgwAuthResult -> {
                            //if not isValid, reject
                            if(!jgwAuthResult.isValid())
                                return errorResponseCreator.errorMessage(exchange,
                                        "SECURITY_ERROR_INVALID_TOKEN",
                                        HttpStatus.FORBIDDEN,
                                        request.getURI().toString(),
                                        "SECURITY_ERROR_INVALID_TOKEN || get (token= "+token+" ) but this is not isValid token  (request="+request.getURI()+")");

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
