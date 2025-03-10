package com.destaxa.payment.api.infraestructure.config;

import com.destaxa.payment.api.application.usecaseimpl.PaymentServiceImpl;
import com.destaxa.payment.api.usecase.PaymentService;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Socket;

@Configuration
public class PaymentConfig {

    @Bean
    public PaymentService paymentService(GenericPackager genericPackager){

        return new PaymentServiceImpl();
    }

}
