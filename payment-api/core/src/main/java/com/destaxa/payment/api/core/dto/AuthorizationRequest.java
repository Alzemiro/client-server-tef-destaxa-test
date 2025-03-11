package com.destaxa.payment.api.core.dto;

public record AuthorizationRequest(
        Integer external_id,
        Double value,
        String card_number,
        String cvv,
        String exp_month,
        String exp_year,
        Integer installments,
        String holder_name
) {
}