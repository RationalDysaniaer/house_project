package com.house.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 指定构建api文档的详细信息的方法：apiInfo()
                .apiInfo(apiInfo()).select()
                // 指定要生成api接口的包路径，这里把controller作为包路径，生成controller 中的所有接口
                .apis(RequestHandlerSelectors.basePackage("com.house.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    /*** 构建api文档的详细信息 * @return */
    private ApiInfo apiInfo()
    { return new ApiInfoBuilder()
            // 设置页面标题
            .title("房屋租赁管理系统接口总览")
            // 设置接口描述
            .description("Swagger2测试")
            // 设置联系方式
            .contact("hello word")
            // 设置版本
            .version("2.0")
            // 构建
            .build();
    }
}

