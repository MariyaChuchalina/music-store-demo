package com.example.musicstoredemo;

import com.example.musicstoredemo.model.Accessory;
import com.example.musicstoredemo.model.Guitar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.musicstoredemo.controller"))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(Accessory.class, Guitar.class);
    }
}
