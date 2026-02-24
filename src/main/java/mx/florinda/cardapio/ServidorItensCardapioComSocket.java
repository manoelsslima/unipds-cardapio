package mx.florinda.cardapio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorItensCardapioComSocket {
    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(8099)) {
            System.out.println("Servidor de itens do cardapio rodando na porta 8099...");

            try (Socket clientSocket = serverSocket.accept()) {
                InputStream clientIS = clientSocket.getInputStream();

                StringBuilder requestBuilder = new StringBuilder();
                int data;
                do {
                    data = clientIS.read();
                    requestBuilder.append((char) data);
                } while (clientIS.available() > 0);

                String request = requestBuilder.toString();
                System.out.println(request);
            }
        }
    }
}
