package br.com.senac.ado.zoologico.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                //Informa
                .info(new Info()
                        .title("Zoologico API - ADO Desenvolvimento Web")
                        .description("API para gerenciamento de um zoológico, incluindo autenticação JWT e documentação OpenAPI.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Erick Guimarães, Daiane Vitoria Raso, Vagner Vieira Beraldo")
                                .email("emailRandomFake@gmail.com")
                        )


                //Habilita o uso do JWT no Swagger UI
                )
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer").bearerFormat("JWT")
                        ))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }

}
