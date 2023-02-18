//package com.jaramgroupware.jaramgateway.config.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.server.DefaultServerRedirectStrategy;
//import org.springframework.security.web.server.ServerRedirectStrategy;
//import org.springframework.security.web.server.WebFilterExchange;
//import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.net.URI;
//
//@Slf4j
//@Component
//public class LoginSuccessHandler implements ServerAuthenticationSuccessHandler {
//    private final ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
//
//    @Override
//    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
//
//        log.debug("LoginSuccessHandler process success");
//        return redirectStrategy.sendRedirect(webFilterExchange.getExchange(), URI.create("/management/service"));
//    }
//}
