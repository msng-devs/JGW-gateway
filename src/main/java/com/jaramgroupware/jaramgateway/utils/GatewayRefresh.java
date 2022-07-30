package com.jaramgroupware.jaramgateway.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 게이트웨이를 갱신시키는 클래스
 * @author hrabit64(37기 황준서)
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class GatewayRefresh {

    @Autowired
    public final ApplicationEventPublisher applicationEventPublisher;

    public void refreshRoutes() {
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
