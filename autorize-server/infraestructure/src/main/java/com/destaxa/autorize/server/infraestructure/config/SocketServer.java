package com.destaxa.autorize.server.infraestructure.config;

import jakarta.annotation.PostConstruct;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SocketServer {

    private final int port = 9999; // Porta do servidor localhost
    private final ExecutorService executor = Executors.newFixedThreadPool(10); // Pool de threads para processar as conexões
    private final GenericPackager packager;

    private final AuthorizationService authorizationService;

    public SocketServer(GenericPackager packager, AuthotizationService authotizationService){
        this.authorizationService = authotizationService;
        this.packager = packager;
    }

    @PostConstruct
    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Socket Server started on: " + port);

            while (!serverSocket.isClosed()){
                Socket clientSocket = serverSocket.accept();
                executor.execute(new ClientHandler(clientSocket, packager, authorizationService));
            }

        } catch (IOException e) {
            System.err.println("Error on server start: " + e.getMessage());
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
                byte[] bytesRead = clientSocket.getInputStream().readNBytes(1024);
                isoMsg.unpack(bytesRead);

                // Processa a mensagem ISO8583
                ISOMsg responseMsg = authorizationService.authorize(isoMsg);

                // Envia a resposta para o cliente
                responseMsg.setPackager(packager);
                byte[] responseBytes = responseMsg.pack();
                clientSocket.getOutputStream().write(responseBytes);

            } catch (IOException | ISOException e) {
                System.err.println("Error while process the client connection: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                }catch (IOException e){
                    System.err.println("Error when client connection is closed: " + e.getMessage());
                }
            }

        }
    }
}

