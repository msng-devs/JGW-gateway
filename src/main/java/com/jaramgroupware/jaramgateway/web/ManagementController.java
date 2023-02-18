//package com.jaramgroupware.jaramgateway.web;
//
//import com.jaramgroupware.jaramgateway.dto.service.ServiceInfoResponseDto;
//import com.jaramgroupware.jaramgateway.service.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.List;
//
//@RequestMapping("/management")
//@RequiredArgsConstructor
//@Controller
//public class ManagementController {
//
//    private final ServiceInfoService serviceInfoService;
//    private final ApiRouteService apiRouteService;
//    private final RoleService roleService;
//    private final MethodService methodService;
//    private final RouteOptionService routeOptionService;
//
//    @GetMapping("/service")
//    public Mono<String> showService(Model model){
//
//        Mono<List<ServiceInfoResponseDto>> services = serviceInfoService.findAllService();
//        model.addAttribute("services",services);
//
//        return Mono.just("service");
//
//    }
//
//    @GetMapping("")
//    public Mono<Void> index(ServerHttpResponse response){
//
//        response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
//        response.getHeaders().setLocation(URI.create("/management/service"));
//        return response.setComplete();
//    }
//
//    @GetMapping("/login")
//    public Mono<String> showLogin(Model model){
//        return Mono.just("login");
//    }
//
//    @GetMapping("/path/{serviceId}")
//    public Mono<String> showPath(Model model, @PathVariable Integer serviceId){
//
//        Mono<String> serviceInfoName = serviceInfoService.findServiceById(serviceId)
//                .flatMap(serviceInfoResponseDto -> {
//                    return Mono.just(serviceInfoResponseDto.getName());
//                });
//
//
//        model.addAttribute("serviceId",serviceId);
//        model.addAttribute("serviceName",serviceInfoName);
//        model.addAttribute("options",routeOptionService.findAllOption());
//        model.addAttribute("paths",apiRouteService.findAllRouteByServiceId(serviceId));
//        model.addAttribute("roles",roleService.findAllRole());
//        model.addAttribute("methods",methodService.findAllMethod());
//
//        return Mono.just("path");
//    }
//}
