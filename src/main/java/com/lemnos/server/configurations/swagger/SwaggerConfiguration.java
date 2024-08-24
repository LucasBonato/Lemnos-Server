package com.lemnos.server.configurations.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(name = "Authorization", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", description = "Access Token", scheme = "bearer", in = SecuritySchemeIn.HEADER)
public interface SwaggerConfiguration {
}
