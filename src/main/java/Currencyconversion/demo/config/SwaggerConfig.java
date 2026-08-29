package Currencyconversion.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI currencyConversionOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Currency Conversion API")
                        .description("REST API for real-time currency conversion using CurrencyBeacon. " +
                                "Converts amounts between any two currencies with automatic rate caching.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Currency Conversion Support")
                                .email("support@currencyconversion.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}
