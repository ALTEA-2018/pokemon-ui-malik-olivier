package com.miage.altea.tp.pokemon_ui.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class RestConfiguration {
    @Value("${trainer.service.username}")
    private String user;

    @Value("${trainer.service.password}")
    private String password;


    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    RestTemplate trainerApiRestTemplate(){
        var restTemplate =  restTemplate();
        restTemplate.setInterceptors(Arrays.asList(new BasicAuthenticationInterceptor(user, password)));
        return restTemplate;
    }
}
