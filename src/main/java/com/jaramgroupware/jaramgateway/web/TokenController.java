package com.jaramgroupware.jaramgateway.web;

import com.google.firebase.auth.FirebaseAuthException;
import com.jaramgroupware.jaramgateway.utils.firebase.TokenCreator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    @Autowired
    private final Environment env;

    @Autowired
    private final TokenCreator tokenCreator;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{uid}")
    public Mono<ResponseEntity<String>> createToken(@PathVariable String uid) throws FirebaseAuthException {
        logger.debug("GET {}",uid);
        if(!env.acceptsProfiles("dev"))
            return Mono.just(ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("BAD_REQUEST check application's profile"));
        return tokenCreator.createToken(uid).map(
                token -> {
                    return new ResponseEntity<String>(token,null,HttpStatus.OK);
                }
        );
    }

    @DeleteMapping("/{uid}")
    public Mono<ResponseEntity<String>> revokeToken(@PathVariable String uid) throws FirebaseAuthException {
        logger.debug("DELETE {}",uid);
        if(!env.acceptsProfiles("dev"))
            return Mono.just(ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("BAD_REQUEST check application's profile"));

        tokenCreator.revokeToken(uid);
        return Mono.just(new ResponseEntity<String>(uid+"'s token is revoked",null,HttpStatus.OK));
    }
}
