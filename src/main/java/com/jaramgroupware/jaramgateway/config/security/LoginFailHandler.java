//package com.jaramgroupware.jaramgateway.config.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.server.DefaultServerRedirectStrategy;
//import org.springframework.security.web.server.ServerRedirectStrategy;
//import org.springframework.security.web.server.WebFilterExchange;
//import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
//import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//
//@Slf4j
//@Component
//public class LoginFailHandler implements ServerAuthenticationFailureHandler {
//
//    private final ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
//
//    @Override
//    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
//        if(exception instanceof BadCredentialsException){
//            log.debug("LoginFailHandler process failed");
//            return redirectStrategy.sendRedirect(webFilterExchange.getExchange(), URI.create("/login?err=fail"));
//        }
//
//        return webFilterExchange.getChain().filter(webFilterExchange.getExchange());
//    }
//}
