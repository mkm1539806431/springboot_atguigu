package com.xjtu.config;

import com.xjtu.component.LoginHandlerInterceptor;
//import com.xjtu.component.MyErrorAttributes;
import com.xjtu.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;

//@EnableWebMvc
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
//public class MyMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/atguigu").setViewName("success");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/asserts/**")
                .addResourceLocations("classpath:/static/asserts/");
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer webMvc = new WebMvcConfigurer(){
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
//                registry.addViewController("/login").setViewName("login");
                registry.addViewController("/login.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");


            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {

                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                                .excludePathPatterns("/login.html","/","/user/login","/webjars/**");
            }

        };

        return webMvc;
    }




//    @Bean
//    public MyErrorAttributes myErrorAttributes(){
//
//        return new MyErrorAttributes();
//    }
    @Bean
    public LocaleResolver localeResolver(){

        return new MyLocaleResolver();
    }

}
