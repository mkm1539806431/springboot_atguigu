package com.xjtu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("mkms");
    }
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("mkm")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xjtu.controller"))
//                扫描的路径，在前面包的基础上，所以为空
                .paths(PathSelectors.ant("/xjtu/**"))
                .build();
    }

    @Bean
    public Docket docket(Environment environment){

//        设置要显示的swagger环境
        Profiles profiles=Profiles.of("dev");
//        通过环境监听变量判断是否处在自己上面设定的环境中
        boolean flag = environment.acceptsProfiles(profiles);
        System.out.println(flag);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                开启swagger
                .groupName("hector")
                .enable(flag)
                .select()
//                RequestHandlerSelectors配置要扫描接口的方式
//                basePackage扫描指定包
//                any()扫描全部
//                none()不扫描
//                withClassAnnotation: 扫描类上的注解
//                withMethodAnnotation：扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.xjtu.controller"))
//                扫描的路径，在前面包的基础上，所以为空
//                .paths(PathSelectors.ant("/xjtu/**"))
                .build()
                ;
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("联系人名字", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
        return new ApiInfo(
                "Swagger学习", // 标题
                "学习演示如何配置Swagger", // 描述
                "v1.0", // 版本
                "http://terms.service.url/组织链接", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "许可链接",// 许可连接
                new ArrayList<VendorExtension>()
        );
    }
}
