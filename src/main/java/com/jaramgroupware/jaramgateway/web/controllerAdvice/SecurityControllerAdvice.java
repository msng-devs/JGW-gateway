//package com.jaramgroupware.jaramgateway.web.controllerAdvice;
//
//import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
//import org.springframework.security.web.server.csrf.CsrfToken;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * ref https://stackoverflow.com/questions/50907381/thymeleaf-webflux-security-csrf-not-added-to-views
// */
//@ControllerAdvice
//public class SecurityControllerAdvice {
//
//    @ModelAttribute("_csrf")
//    Mono<CsrfToken> csrfToken(ServerWebExchange exchange) {
//        Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
//        if(csrfToken == null) return Mono.empty();
//
//        return csrfToken.doOnSuccess(token -> exchange.getAttributes()
//                .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
//    }
//}
