package com.jaramgroupware.jaramgateway.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 자람 그룹웨어의 Gateway의 firebase admin 설정 클래스입니다.
 * firebase.json에 담겨진 시크릿키를 사용하여 gateway를 firebase app으로 등록시킵니다.
 *
 * @author hrabit64(37기 황준서)
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class FirebaseConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        logger.info("Initializing Firebase.");
        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/firebase.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("heroku-sample.appspot.com")
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        logger.info("FirebaseApp initialized" + app.getName());
        return app;
    }


    @Bean
    public FirebaseAuth getFirebaseAuth() throws IOException {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp());
        return firebaseAuth;
    }
}
