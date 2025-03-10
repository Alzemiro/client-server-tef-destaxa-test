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
    public ISOMsg toIsoMessage(AuthorizationRequest request) throws ISOException {
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setMTI("0200"); // Define o MTI para "0200" (Solicitação de Venda com Cartão de Crédito)
        isoMsg.set(2, request.card_number()); // Número do cartão (BIT 2)
        isoMsg.set(3, request.processing_code()); // Código de processamento (BIT 3)
        isoMsg.set(4, String.format("%.2f", request.value()).replace(".", "")); // Valor da transação (BIT 4) - Formatado como numérico sem separador decimal
        isoMsg.set(7, DateTimeFormat.forPattern("MMddHHmmss").print(DateTime.now())); // Data/Hora Transmissão GMT (BIT 7) - Formato MMDDHHMMSS
        isoMsg.set(11, NsuGenerator.generateNsu()); // NSU (Número Sequencial Único) (BIT 11) - Gerado aleatoriamente
        isoMsg.set(12, DateTimeFormat.forPattern("HHmmss").print(DateTime.now())); // Hora local da transação (BIT 12) - Formato HHMMSS
        isoMsg.set(13, DateTimeFormat.forPattern("MMdd").print(DateTime.now())); // Data local da transação (BIT 13) - Formato MMDD
        isoMsg.set(14, request.exp_month() + request.exp_year().substring(2)); // Data de vencimento do cartão (BIT 14) - Formato AAMM
        isoMsg.set(22, request.pointOfServiceEntryMode()); // Modo de entrada (BIT 22)
        isoMsg.set(42, request.cardAcceptorIdentificationCode()); // Código do Estabelecimento (BIT 42)
        isoMsg.set(67, String.format("%02d", request.installments())); // Número de Parcelas (BIT 67) - Formatado com 2 dígitos
        return isoMsg;
    }

    @Override
    public AuthorizationResponse toAuthorizationResponse(ISOMsg isoMsg) {
        return new AuthorizationResponse(
                isoMsg.getString(127),
                Double.parseDouble(isoMsg.getString(4)),
                isoMsg.getString(39),
                isoMsg.getString(38),
                isoMsg.getString(13),
                isoMsg.getString(12)
        );
    }
}
