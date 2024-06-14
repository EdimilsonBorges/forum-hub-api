package com.edimilsonborges.forum_hub.springDoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Forum Hub API")
                        .version("1.0")
                        .description("""
                                A Forum Hub API é uma API RESTful desenvolvida em Spring Boot com Java que fornece funcionalidades para um fórum online.</br>
                                Os usuários podem se cadastrar, fazer login, criar tópicos com suas dúvidas e responder aos tópicos de outros usuários.</br>
                                A API foi projetada para ser simples e intuitiva, permitindo uma interação fluida e eficiente com o fórum.</br></br>
                                A Forum Hub API utiliza JSON Web Tokens (JWT) para autenticação e autorização.</br> Os endpoints críticos como a criação de tópicos e respostas exigem um token JWT válido no cabeçalho da solicitação.
                                """))
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .tags(Arrays.asList(
                        new Tag().name("Public Controller").description("Public API Endpoints")
                ));
    }
}
