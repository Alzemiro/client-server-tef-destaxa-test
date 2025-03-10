package com.destaxa.payment.api.infraestructure.controller;

import com.destaxa.payment.api.core.dto.AuthorizationRequest;
import com.destaxa.payment.api.core.dto.AuthorizationResponse;
import com.destaxa.payment.api.core.mapper.AuthorizationMapper;
import com.destaxa.payment.api.usecase.PaymentService;
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

    public PaymentController(PaymentService paymentService, AuthorizationMapper authorizationMapper){
        this.paymentService = paymentService;
        this.authorizationMapper = authorizationMapper;
    }

    @PostMapping("/authorization")
    public ResponseEntity<AuthorizationResponse> authorizePayment(@RequestBody AuthorizationRequest request) throws ISOException, IOException {
        ISOMsg isoMsg = authorizationMapper.toIsoMessage(request);

        isoMsg.dump(System.out, "");

        ISOMsg responseMsg = paymentService.authorize(isoMsg);

        AuthorizationResponse authorizationResponse = authorizationMapper.toAuthorizationResponse(responseMsg);

        return ResponseEntity.ok(authorizationResponse);
    }

}
