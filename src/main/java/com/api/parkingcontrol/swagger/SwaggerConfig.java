package com.api.parkingcontrol.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName("Parking")
                .tags(new Tag("Parking", "Endpoints para operações CRUD em Parking"))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Parking Control")
                .description("Aplicação para controle de estacionamento de veículos")
                .version("1.0.0")
                .license("")
                .licenseUrl("")
                .contact(new Contact("Alexandre Riff da Costa | Claiton Ricardo Machado Barchet", " ", " "))
                .build();
    }

}
