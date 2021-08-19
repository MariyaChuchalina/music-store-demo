package com.example.musicstoredemo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "access")
public class HeaderConfig {

    @Value("${header}")
    private String accessHeader;
}
