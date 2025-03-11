package com.destaxa.payment.api.infraestructure.controller;

import com.destaxa.payment.api.core.dto.AuthorizationRequest;
import com.destaxa.payment.api.core.dto.AuthorizationResponse;
import com.destaxa.payment.api.core.exception.AuthorizationException;
import com.destaxa.payment.api.core.mapper.AuthorizationMapper;
import com.destaxa.payment.api.usecase.MessageEmptyUseCase;
import com.destaxa.payment.api.usecase.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PaymentController {

    private final PaymentService paymentService;
    private final AuthorizationMapper authorizationMapper;
    private final MessageEmptyUseCase messageEmptyUseCase;

    public PaymentController(PaymentService paymentService, AuthorizationMapper authorizationMapper, MessageEmptyUseCase messageEmptyUseCase){
        this.paymentService = paymentService;
        this.authorizationMapper = authorizationMapper;
        this.messageEmptyUseCase = messageEmptyUseCase;
    }

    @PostMapping("/authorization")
    public ResponseEntity<AuthorizationResponse> authorizePayment(@RequestBody AuthorizationRequest request, HttpServletRequest httpServletRequest) throws ISOException, IOException {
        String xIdentifier = httpServletRequest.getHeader("x-identifier");
        ISOMsg isoMsg = authorizationMapper.toIsoMessage(request, xIdentifier);

        ISOMsg responseMsg = paymentService.authorize(isoMsg);

        if(messageEmptyUseCase.isEmpty(responseMsg)){
            throw new AuthorizationException();
        }

        AuthorizationResponse authorizationResponse = authorizationMapper.toAuthorizationResponse(responseMsg);

        return ResponseEntity.ok(authorizationResponse);
    }

}
