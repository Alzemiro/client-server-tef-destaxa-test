package com.destaxa.payment.api.infraestructure.mapper;

import com.destaxa.payment.api.core.dto.AuthorizationRequest;
import com.destaxa.payment.api.core.dto.AuthorizationResponse;
import com.destaxa.payment.api.core.mapper.AuthorizationMapper;
import com.destaxa.payment.api.infraestructure.util.NsuGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import org.springframework.stereotype.Component;

@Component
public class AuthorizationMapperImpl implements AuthorizationMapper {



    @Override
    public ISOMsg toIsoMessage(AuthorizationRequest request, String xIdentifier) throws ISOException {
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setHeader("ISO1987".getBytes());
        isoMsg.setMTI("0200");
        isoMsg.set(2, request.card_number());
        isoMsg.set(3, request.installments() > 1 ? "003001" : "003000");
        isoMsg.set(4, String.format("%.2f", request.value()).replace(".", ""));
        isoMsg.set(7, DateTimeFormat.forPattern("MMddHHmmss").print(DateTime.now()));
        isoMsg.set(11, NsuGenerator.generateNsu());
        isoMsg.set(12, DateTimeFormat.forPattern("HHmmss").print(DateTime.now()));
        isoMsg.set(13, DateTimeFormat.forPattern("MMdd").print(DateTime.now()));
        isoMsg.set(42, xIdentifier);
        isoMsg.set(48, request.cvv());
        isoMsg.set(67, String.format("%02d", request.installments()));
        return isoMsg;
    }

    @Override
    public AuthorizationResponse toAuthorizationResponse(ISOMsg isoMsg) {
        isoMsg.setHeader("ISO1987".getBytes());
        Double value = isoMsg.getString(4) != null ? Double.parseDouble(isoMsg.getString(4).replaceFirst("^0+(?!$)", "").replace(",", ".")) : null;

        return new AuthorizationResponse(
                isoMsg.getString(127),
                value,
                isoMsg.getString(39),
                isoMsg.getString(38),
                DateTimeFormat.forPattern("yy-MM-dd").print(DateTime.now()),
                DateTimeFormat.forPattern("HH:mm:ss").print(DateTime.now())
        );
    }
}
