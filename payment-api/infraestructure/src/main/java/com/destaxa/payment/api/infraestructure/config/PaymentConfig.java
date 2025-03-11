package com.destaxa.payment.api.infraestructure.config;

import com.destaxa.payment.api.application.usecaseimpl.PaymentServiceImpl;
import com.destaxa.payment.api.usecase.PaymentService;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    @Bean
    public PaymentService paymentService(GenericPackager genericPackager){

        PaymentServiceImpl paymentService = new PaymentServiceImpl();
        paymentService.setPackager(genericPackager);

        return paymentService;
    }

}
