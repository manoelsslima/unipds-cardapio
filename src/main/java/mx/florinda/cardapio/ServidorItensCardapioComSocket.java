package mx.florinda.cardapio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServidorItensCardapioComSocket {
    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(8099)) {
            System.out.println("Servidor de itens do cardapio rodando na porta 8099...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    InputStream clientIS = clientSocket.getInputStream();

                    StringBuilder requestBuilder = new StringBuilder();
                    int data;
                    do {
                        data = clientIS.read();
                        requestBuilder.append((char) data);
                    } while (clientIS.available() > 0);

                    String request = requestBuilder.toString();
                    System.out.println(request); // out é um PrintStream

                    Path path = Path.of("itensCardapio.json");
                    String json = Files.readString(path);

                    OutputStream clientOS = clientSocket.getOutputStream();
                    PrintStream clientOut = new PrintStream(clientOS); // crio o PrintStream para enviar para o out

                    clientOut.println("HTTP/1.1 200 OK");
                    clientOut.println("Content-Type: application/json; charset=UTF-8");
                    clientOut.println("Content-Length: " + json.getBytes().length);
                    clientOut.println(); // linha em branco para separar os headers do body
                    clientOut.println(json);
                }
            }
        }
    }
}
