package com.destaxa.payment.api.infraestructure.config;

import com.destaxa.payment.api.application.usecaseimpl.PaymentServiceImpl;
import com.destaxa.payment.api.usecase.PaymentService;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    public PaymentService paymentService(){
        return new PaymentServiceImpl();
    }

}
