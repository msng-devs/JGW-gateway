package com.jaramgroupware.jaramgateway.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig{

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {

        http
                .securityMatcher(new CustomNegatedServerWebExchangeMatcher())
                .authorizeExchange()
                    .pathMatchers("/management/login/**", "/static/**","/login","/console/**", "/reset").permitAll()
                    .pathMatchers("/management/**").authenticated()
                    .anyExchange().authenticated()
                .and()
                .formLogin()
                    .loginPage("/management/login")
                    .authenticationSuccessHandler(new LoginSuccessHandler())
                    .authenticationFailureHandler(new LoginFailHandler())

                .and()
                .logout()
                    .logoutSuccessHandler(new LoginOutHandler())
                .and()
                .csrf()
                    .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse());

        return http.build();
    }



}
