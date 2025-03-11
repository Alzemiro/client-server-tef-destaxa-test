package com.destaxa.payment.api.application.usecaseimpl;

import com.destaxa.payment.api.usecase.PaymentService;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;


public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private GenericPackager packager;

    @Override
    public ISOMsg authorize(ISOMsg isoMsg) throws IOException, ISOException {
        ISOMsg responseMsg = new ISOMsg();

        logger.info("Sending ISO8583 message to server: {}", isoMsg);
        isoMsg.dump(System.out, "");

        try(Socket socket = new Socket("localhost", 9999)){
            isoMsg.setPackager(packager);

            // Envia mensagem ISO8583 para o servidor
            byte[] isoBytes = isoMsg.pack();
            socket.getOutputStream().write(isoBytes);

            // Recebe a resposta do servidor
            responseMsg.setPackager(this.packager);
            BufferedInputStream bytesRead = new BufferedInputStream(socket.getInputStream());

            responseMsg.unpack(bytesRead);

        }catch (IOException | ISOException e){
            logger.error("Error authorizing transaction: {}", e.getMessage());
        }

        logger.info("Received ISO8583 response from server: {}", responseMsg);
        responseMsg.dump(System.out, "");

        return responseMsg;
    }

    public void setPackager(GenericPackager packager){
        this.packager = packager;
    }


}
