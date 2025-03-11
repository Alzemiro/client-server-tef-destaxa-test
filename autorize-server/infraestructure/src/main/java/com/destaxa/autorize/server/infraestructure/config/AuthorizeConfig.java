package com.destaxa.autorize.server.infraestructure.config;

import com.destaxa.autorize.server.infraestructure.application.usecaseimpl.AuthorizationServiceImpl;
import com.destaxa.autorize.server.infraestructure.application.usecaseimpl.CalculateAuthorizationCodeImpl;
import com.destaxa.autorize.server.infraestructure.usecase.AuthorizationService;
import com.destaxa.autorize.server.infraestructure.usecase.CalculateAuthorizationCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizeConfig {

    @Bean
    public AuthorizationService authorizationService(CalculateAuthorizationCode calculateAuthorizationCode){
        AuthorizationServiceImpl authorizationService = new AuthorizationServiceImpl();
        authorizationService.setCalculateAuthorizationCode(calculateAuthorizationCode);
        return authorizationService;
    }

    @Bean
    public CalculateAuthorizationCode calculateAuthorizationCode(){
        return new CalculateAuthorizationCodeImpl();
    }

}
