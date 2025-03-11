package com.destaxa.autorize.server.infraestructure.config;

import com.destaxa.autorize.server.infraestructure.application.usecaseimpl.AuthorizationServiceImpl;
import com.destaxa.autorize.server.infraestructure.usecase.AuthorizationService;
import com.destaxa.autorize.server.infraestructure.usecase.CalculateAuthorizationCode;
import jakarta.annotation.PostConstruct;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SocketServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private final int port = 9999; // Porta do servidor localhost
    private final ExecutorService executor = Executors.newFixedThreadPool(10); // Pool de threads para processar as conexões
    private final GenericPackager packager;

    private final AuthorizationService authorizationService;

    public SocketServer(GenericPackager packager, AuthorizationService authotizationService){
        this.authorizationService = authotizationService;
        this.packager = packager;
    }

    @PostConstruct
    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("Socket Server started on port {}", port);

            while (true){
                Socket clientSocket = serverSocket.accept();
                executor.execute(new ClientHandler(clientSocket, packager, authorizationService));
            }

        } catch (IOException e) {
            logger.error("Error on server start: {}", e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;
        private final GenericPackager packager;
        private final AuthorizationService authorizationService;

        public ClientHandler(Socket clientSocket, GenericPackager packager, AuthorizationService authorizationService){
            this.clientSocket = clientSocket;
            this.packager = packager;
            this.authorizationService = authorizationService;

        }

        @Override
        public void run() {

            try {
                // Lê a mensagem ISO8583 do cliente
                ISOMsg isoMsg = new ISOMsg();
                isoMsg.setPackager(packager);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());

                isoMsg.unpack(bufferedInputStream);
                logger.info("Received ISO8583 message: {}", isoMsg);
                isoMsg.dump(System.out, "");

                // Processa a mensagem ISO8583
                ISOMsg responseMsg = authorizationService.authorize(isoMsg);
                responseMsg.setHeader("ISO1987".getBytes());

                logger.info("Sending ISO8583 response: {}", responseMsg);
                responseMsg.dump(System.out, "");

                // Envia a resposta para o cliente
                responseMsg.setPackager(packager);
                byte[] responseBytes = responseMsg.pack();
                clientSocket.getOutputStream().write(responseBytes);

            } catch (IOException | ISOException e) {
                logger.error("Error while processing client connection: {}", e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                }catch (IOException e){
                    logger.error("Error when closing client connection: {}", e.getMessage());
                }
            }

        }
    }
}

