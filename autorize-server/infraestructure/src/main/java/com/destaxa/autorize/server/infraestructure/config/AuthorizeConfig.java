package com.destaxa.autorize.server.infraestructure.config;

import com.destaxa.autorize.server.infraestructure.application.usecaseimpl.AuthorizationServiceImpl;
import com.destaxa.autorize.server.infraestructure.usecase.AuthorizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizeConfig {

    @Bean
    public AuthorizationService authorizationService(){
        return new AuthorizationServiceImpl();
    }

}
