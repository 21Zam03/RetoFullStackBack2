package com.zam.prueba.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("PRUEBA_FULL_STACK")
                        .description("Documentacion de los servicios rest")
                        .version("1.0")
                        .contact(new Contact().email("21jose03.free@gmail.com")
                                .name("21ZAM03"))
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

}
