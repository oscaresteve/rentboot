package com.oscaresteve.rentboot.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "Rentboot API",
        version = "1.0",
        description = "Documentacion OpenAPI para la API REST de Rentboot",
        contact = @Contact(name = "Oscar Esteve", email = "oscaresteve@demo.local"),
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")
    ),
    servers = {
        @Server(description = "Local ENV", url = "http://localhost:8090")
    }
)
public class OpenApiConfig {
}
