package ar.edu.uade.integracion.shop.config;

import org.eclipse.jetty.client.api.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerCofig {
    @Bean
    public Docket api() {
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new ApiKey(HttpHeaders.AUTHORIZATION, "Authorization", "header"));
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ar.edu.uade.integracion.shop.controller"))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(Authentication.class)
                .produces(Collections.singleton("application/json"))
                .consumes(Collections.singleton("application/json"))
                .securitySchemes(schemeList)
                .apiInfo(apiInfo())
                ;
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger Shop")
                .description("Here you will find all the operations related to the shop.")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("Pablo De Maio", "Demaio.com", "pdemaio@uade.edu.ar"))
                .build();
    }
}
