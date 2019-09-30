package com.mnidecki.cardoor.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Getter
@Component
public class KayakConfig {

    @Value("${kayak.api.car.endpoint}")
    private String kayakCarEndpoint;

    @Value("${kayak.api.location.endpoint}")
    private String kayakLocationEndpoint;

    @Value("${kayak.api.header.key.name}")
    private String kayakHeaderKeyName;

    @Value("${kayak.api.header.key.value}")
    private String kayakHeaderKeyValue;

    @Value("${kayak.api.header.host.name}")
    private String kayakHeaderHostName;

    @Value("${kayak.api.header.host.value}")
    private String kayakHeaderHostValue;
}
