//package com.jaramgroupware.jaramgateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.web.reactive.config.EnableWebFlux;
//import org.springframework.web.reactive.config.ViewResolverRegistry;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
//import org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver;
//
//@Configuration
//@EnableWebFlux
//public class WebConfig implements WebFluxConfigurer {
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.;
//    }
//
//    @Bean
//    RouterFunction<ServerResponse> staticResourceRouter(){
//        return RouterFunctions.resources("/**", new ClassPathResource("static/"));
//    }
//
////    @Bean
////    public FreeMarkerConfigurer freeMarkerConfigurer() {
////        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
////        configurer.setTemplateLoaderPath("classpath:/templates");
////        return configurer;
////    }
//}
