package com.destaxa.autorize.server.infraestructure.application.usecaseimpl;

import com.destaxa.autorize.server.infraestructure.usecase.AuthorizationService;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public ISOMsg authorize(ISOMsg isoMsg) throws ISOException {
        // Obter o valor da transação
        double value = Double.parseDouble(isoMsg.getString(4));

        // Aplicar regra de negócio
        String responseCode;
        if(value > 1000){
            // Timeout
            return null;
        } else if(value % 2 == 0){
            responseCode = "000";
        } else {
            responseCode = "051";
        }

        // Gerar a mensagem ISO8583 da resposta
        ISOMsg responseMsg = new ISOMsg();
        responseMsg.setMTI("0210");
        responseMsg.set(4, isoMsg.getString(4));
        responseMsg.set(7, isoMsg.getString(7));
        responseMsg.set(11, isoMsg.getString(11));
        responseMsg.set(12, isoMsg.getString(12));
        responseMsg.set(13, isoMsg.getString(13));
        responseMsg.set(39, responseCode);
        responseMsg.set(42, isoMsg.getString(42));
        responseMsg.set(127, "1234567890");

        return responseMsg;

    }
}
