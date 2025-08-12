package com.organizaAi.OrganizaAi.infra;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
public OpenAPI customOpenAPI() {
    final String securitySchemeName = "bearerAuth";

    Server server = new Server();
    server.setUrl("https://zany-lamp-p6v9qww557xc6xr4-8080.app.github.dev/");
    server.setUrl("http://localhost:8081/");

    return new OpenAPI()
            .info(new Info().title("OrganizaAi API").version("1.0").description("Documentação da API OrganizaAi"))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components()
                    .addSecuritySchemes(securitySchemeName,
                            new SecurityScheme()
                                    .name(securitySchemeName)
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")
                    )
            )
            .servers(List.of(server)); 
}
}