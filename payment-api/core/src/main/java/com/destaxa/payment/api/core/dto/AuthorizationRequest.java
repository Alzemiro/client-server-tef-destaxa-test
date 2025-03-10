package com.destaxa.payment.api.core.dto;

public record AuthorizationRequest(
        String card_number, // Número do cartão (BIT 2)
        Double value,
        String processing_code,
        String exp_month,
        String exp_year,
        Integer installments,
        String pointOfServiceEntryMode,
        String cardAcceptorIdentificationCode
) {
}