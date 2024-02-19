package me.kauepalota.sicredi.techchallenge.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Sicredi Tech Challenge",
        version = "1.0",
        description = "API for Sicredi Tech Challenge"
    )
)
class SwaggerConfiguration