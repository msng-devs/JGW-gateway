package com.jaramgroupware.jaramgateway.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.RequestPath;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
public class CustomNegatedServerWebExchangeMatcher implements ServerWebExchangeMatcher {

    private final String regexPattern = "^/management/|^/management";
    private final List<String> needMatchPaths = Arrays.asList("/login","/logout");

    @Override
    public Mono<MatchResult> matches(ServerWebExchange exchange) {

        String path = exchange.getRequest().getPath().toString();
        log.debug("target path {}",path);
        if(path.matches(regexPattern) || needMatchPaths.contains(path)){
            log.debug("target matched");
            return MatchResult.match();
        }
        else {
            log.debug("not matched");
            return MatchResult.notMatch();
        }

    }
}
