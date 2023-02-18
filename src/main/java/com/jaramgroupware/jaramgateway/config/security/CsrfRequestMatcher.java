//package com.jaramgroupware.jaramgateway.config.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//public class CsrfRequestMatcher implements ServerWebExchangeMatcher {
////    private final List<String> notNeedCsrfPaths = Arrays.asList("/login");
//
//    @Override
//    public Mono<MatchResult> matches(ServerWebExchange exchange) {
//        String path = exchange.getRequest().getPath().toString();
////        if(notNeedCsrfPaths.contains(path)) {
////            log.debug("CsrfRequestMatcher not matched path -> {}",path);
////            return MatchResult.notMatch();
////        }
//        if(exchange.getRequest().getMethod() == HttpMethod.GET){
//            log.debug("CsrfRequestMatcher not matched path -> [{}]{}",exchange.getRequest().getMethod(),path);
//            return MatchResult.notMatch();
//        }
//        log.debug("CsrfRequestMatcher matched path -> [{}]{}",exchange.getRequest().getMethod(),path);
//        return MatchResult.match();
//    }
//}
