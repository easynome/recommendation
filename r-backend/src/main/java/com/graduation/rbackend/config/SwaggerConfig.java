//package com.graduation.rbackend.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//import java.util.Collections;
//
//
///**
// * 配置Swagger，生成API文档
// */
//@Configuration
//
//public class SwaggerConfig {
//    private static final String BASE_PACKAGE = "com.graduation.rbackend.controller";
//    private static final String VERSION = "1.0.0";
//
//    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);
//    @Bean
//    public Docket api(){
//
//        try {
//            return new Docket(DocumentationType.SWAGGER_2)
//                    .select()
//                    .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
//                    .paths(PathSelectors.any())
//                    .build()
//                    .apiInfo(new springfox.documentation.service.ApiInfo(
//                            "Recommendation System API",//文档标题
//                            "API documentation for Recommendation System",
//                            VERSION,
//                            null,
//                            null,
//                            null,
//                            null,
//                            Collections.emptyList()
//                    ));
//        } catch (Exception e) {
//            logger.error("Swagger configuration error: {}", e.getMessage());
//            throw new RuntimeException("Failed to initialize",e);
//        }
//    }
//}

/**
 * version-2
 */

package com.graduation.rbackend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.core.env.Environment;

import java.util.Collections;

/**
 * 配置Swagger，生成API文档
 */
@Configuration
public class SwaggerConfig {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    private final Environment env;

    public SwaggerConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        try {
            String basePackage = env.getProperty("swagger.base.package", "com.graduation.rbackend.controller");
            String version = env.getProperty("swagger.version", "1.0.0");

            return new OpenAPI()
                    .info(new Info()
                            .title(env.getProperty("swagger.title", "Recommendation System API"))
                            .description(env.getProperty("swagger.description", "API documentation for Recommendation System"))
                            .version(version));
        } catch (Exception e) {
            logger.error("Swagger configuration error: ", e);
            throw new IllegalStateException("Failed to initialize Swagger configuration", e);
        }
    }
}