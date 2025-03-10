package com.destaxa.payment.api.core.mapper;

import com.destaxa.payment.api.core.dto.AuthorizationRequest;
import com.destaxa.payment.api.core.dto.AuthorizationResponse;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public interface AuthorizationMapper {
    ISOMsg toIsoMessage(AuthorizationRequest request) throws ISOException;
    AuthorizationResponse toAuthorizationResponse(ISOMsg isoMsg);
}
