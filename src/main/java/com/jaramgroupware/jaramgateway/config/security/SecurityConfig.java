//package com.jaramgroupware.jaramgateway.config.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig{
//
//
//    @Bean
//    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
//
//
//
//        http
//                .securityMatcher(new SecurityRequestMatcher())
//                .authorizeExchange()
//                    .pathMatchers("/management/login/**","/login").permitAll()
//                    .anyExchange().authenticated()
//                .and()
//                .formLogin()
//                    .loginPage("/management/login")
//                    .authenticationSuccessHandler(new LoginSuccessHandler())
//                    .authenticationFailureHandler(new LoginFailHandler())
//                .and()
//                .logout()
//                    .logoutSuccessHandler(new LoginOutHandler())
//                .and()
//                .csrf()
//                    .requireCsrfProtectionMatcher(new CsrfRequestMatcher())
//                .and()
//                .exceptionHandling()
//                    .accessDeniedHandler(new HttpStatusServerAccessDeniedHandler(HttpStatus.BAD_REQUEST))
//                .and()
//                .httpBasic();
//
//        return http.build();
//    }
//
//
//
//}
