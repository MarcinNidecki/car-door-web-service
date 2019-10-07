package com.mnidecki.cardoor.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FtpConfig {
    @Value("${ftp.host}")
    private String ftpHost;
    @Value("${ftp.login}")
    private String login;
    @Value("${ftp.password}")
    private String password;
    @Value("${ftp.domain.image.path}")
    private String domainFullImagePath;

}