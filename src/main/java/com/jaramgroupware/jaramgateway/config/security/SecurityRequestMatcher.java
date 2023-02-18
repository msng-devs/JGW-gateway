//package com.jaramgroupware.jaramgateway.config.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.server.RequestPath;
//import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Slf4j
//public class SecurityRequestMatcher implements ServerWebExchangeMatcher {
//
//    private final String regexPattern = "(^\\/management\\/|^\\/management)";
//    private final List<String> needMatchPaths = Arrays.asList("/login","/logout");
//    private final Pattern pattern = Pattern.compile(regexPattern, Pattern.MULTILINE);
//    @Override
//    public Mono<MatchResult> matches(ServerWebExchange exchange) {
//
//        String path = exchange.getRequest().getPath().toString();
//
//        if(pattern.matcher(path).find()|| needMatchPaths.contains(path)){
//
//            log.debug("SecurityRequestMatcher matched path -> {}",path);
//            return MatchResult.match();
//        }
//        else {
//            log.debug("SecurityRequestMatcher not matched path -> {}",path);
//            return MatchResult.notMatch();
//        }
//
//    }
//}
