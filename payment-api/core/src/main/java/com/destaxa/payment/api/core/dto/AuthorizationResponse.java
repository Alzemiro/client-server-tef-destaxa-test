package com.destaxa.payment.api.core.dto;

public record AuthorizationResponse(String payment_id,
                                    Double value,
                                    String response_code,
                                    String authorization_code,
                                    String transaction_date,
                                    String transaction_hour) {
}
