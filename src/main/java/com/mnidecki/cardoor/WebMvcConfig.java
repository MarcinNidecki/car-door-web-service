package com.mnidecki.cardoor;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.List;

@Configuration
@EnableJpaRepositories
public class WebMvcConfig implements WebMvcConfigurer {
    ApplicationContext applicationContext;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/bookingConfirmation").setViewName("bookingConfirmation");
        registry.addViewController("/cars").setViewName("cars");
        registry.addViewController("/admin").setViewName("cars");
        registry.addViewController("/carsPictures").setViewName("carsPicture");
        registry.addViewController("/brand").setViewName("carBrand");
        registry.addViewController("/carTypes").setViewName("carType");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/hello").setViewName("accountVerified");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/booking").setViewName("booking");
        registry.addViewController("/carBooking").setViewName("carBooking");
    }

    //1. Creating SpringResourceTemplateResolver
    @Bean
    public SpringResourceTemplateResolver springTemplateResolver() {
        SpringResourceTemplateResolver springTemplateResolver = new SpringResourceTemplateResolver();
        springTemplateResolver.setApplicationContext(this.applicationContext);
        springTemplateResolver.setPrefix("classpath:/templates/");
        springTemplateResolver.setSuffix(".html");
        return springTemplateResolver;
    }

    //2. Creating SpringTemplateEngine
    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(springTemplateResolver());
        springTemplateEngine.addDialect(new SpringSecurityDialect());


        return springTemplateEngine;
    }


    //3. Registering ThymeleafViewResolver
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine());
        return viewResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
    }

}