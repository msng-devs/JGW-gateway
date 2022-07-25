package com.jaramgroupware.jaramgateway.utils.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TokenCreator {

    @Autowired
    private final FirebaseAuth firebaseAuth;

    public Mono<String> createToken(String uid) throws FirebaseAuthException {
        return Mono.just(firebaseAuth.createCustomToken(uid));
    }

    public void revokeToken(String uid) throws FirebaseAuthException {
        firebaseAuth.revokeRefreshTokens(uid);
    }
}
