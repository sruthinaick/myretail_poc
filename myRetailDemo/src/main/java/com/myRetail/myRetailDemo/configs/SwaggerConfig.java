package com.myRetail.myRetailDemo.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration Class to configuration of Swagger UI. Swagger UI gives us a nice and flexible UI
 * to Display all our hosted services and operations on them Some sample input to execute the
 * operation on exposed webservice.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Default api information which will be displayed to the Swagger UI front-end.
     *
     * @return
     */
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "My Retail API",
                "The goal for this exercise is to create an <strong>end-to-end Proof-of-Concept</strong> for a products API, " +
                        "which will aggregate product data from multiple sources and return it as JSON to the " +
                        "caller.Your goal is to create a RESTful service that can retrieve product and price " +
                        "details by ID. ",
                "API TOS",
                "Terms of service",
                new Contact("Sruthi", " ", " "),
                "License of API",
                "API license URL");
        return apiInfo;
    }
}