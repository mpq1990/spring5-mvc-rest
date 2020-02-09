package guru.springfamework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).
                select().
                apis(RequestHandlerSelectors.any()).
                paths(PathSelectors.any()).
                build().
                pathMapping("/").
                apiInfo(metaData()).
                tags(new Tag("customerControllerTag", "This is our customer controller.")).tags(
                new Tag("vendorControllerTag", "This is our vendor controller")
        );
    }

    private ApiInfo metaData() {
        Contact contact = new Contact("Test", "https://www.google.com", "test@test.de");

        return new ApiInfo("Awesome Api", "Demo rest app", "1.0", "what eva",
                contact, "Apache Licence Version 2.0", "google it", new ArrayList<>());
    }


}
