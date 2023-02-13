package com.jaramgroupware.jaramgateway.web;

import com.jaramgroupware.jaramgateway.dto.service.ServiceInfoResponseDto;
import com.jaramgroupware.jaramgateway.service.ServiceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/management")
@RequiredArgsConstructor
@Controller
public class ManagementController {

    private final ServiceInfoService serviceInfoService;

    //TODO Non-blocking으로 수정하기
    @GetMapping("/service")
    public Mono<String> showService(Model model){

        List<ServiceInfoResponseDto> services = serviceInfoService.findAllService().block();
        model.addAttribute("services",services);

        return Mono.just("service");

    }

    @GetMapping("/login")
    public Mono<String> showLogin(Model model){

        return Mono.just("login");
    }

    @GetMapping("/path/{serviceId}")
    public Mono<String> showPath(Model model, @PathVariable Integer serviceId){

        return Mono.just("path");
    }
}
