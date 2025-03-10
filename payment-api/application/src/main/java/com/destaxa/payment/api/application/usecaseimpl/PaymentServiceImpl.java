package com.destaxa.payment.api.application.usecaseimpl;

import com.destaxa.payment.api.usecase.PaymentService;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

@Component
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private GenericPackager packager;

    @Override
    public ISOMsg authorize(ISOMsg isoMsg) throws IOException, ISOException {
        Socket socket = new Socket("localhost", 9999);
        isoMsg.setPackager(packager);


        // Envia mensagem ISO8583 para o servidor
        byte[] isoBytes = isoMsg.pack();;
        socket.getOutputStream().write(isoBytes);

        // Recebe a resposta do servidor
        ISOMsg responseMsg = new ISOMsg();
        responseMsg.setPackager(this.packager);
        byte[] bytesRead = socket.getInputStream().readNBytes(1024);
        responseMsg.unpack(bytesRead);

        return responseMsg;
    }

}
