package com.mnidecki.cardoor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VinDecoderConfig {

    @Value("${vin.decoder.api.endpoint}")
    private String vinDecoderEndpoint;

    @Value("${vin.decoder.api.header.key.name}")
    private  String vinDecoderHeaderKeyName;

    @Value("${vin.decoder.api.header.key.value}")
    private String vinDecoderHeaderKeyValue;

    @Value("${vin.decoder.api.header.host.name}")
    private  String vinDecoderHeaderHostName;

    @Value("${vin.decoder.api.header.host.value}")
    private String vinDecoderHeaderHostValue;


}

