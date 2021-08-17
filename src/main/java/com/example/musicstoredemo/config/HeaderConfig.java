package com.example.musicstoredemo.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class HeaderConfig {

    private String accessHeader;
}
