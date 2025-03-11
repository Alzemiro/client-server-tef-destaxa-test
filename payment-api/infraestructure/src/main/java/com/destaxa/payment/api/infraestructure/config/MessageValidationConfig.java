package com.destaxa.payment.api.infraestructure.config;

import com.destaxa.payment.api.application.usecaseimpl.MessageEmptyUseCaseImpl;
import com.destaxa.payment.api.usecase.MessageEmptyUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageValidationConfig {

    @Bean
    public MessageEmptyUseCase messageEmptyUseCase(){
        return new MessageEmptyUseCaseImpl();
    }

}
