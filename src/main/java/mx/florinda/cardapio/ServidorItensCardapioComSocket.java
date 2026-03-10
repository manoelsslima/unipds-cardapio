package mx.florinda.cardapio;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorItensCardapioComSocket {

    private static final Database database = new Database();

    public static void main(String[] args) throws IOException {

        // limitando em 50 o número de threads. Sugestão: no máximo o número de cpus
        try (ExecutorService executor = Executors.newFixedThreadPool(50)) {

            try (ServerSocket serverSocket = new ServerSocket(8099)) {
                System.out.println("Servidor de itens do cardapio rodando na porta 8099...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    // Thread thread = new Thread(() -> trataRequisicao(clientSocket));
                    executor.execute(() -> trataRequisicao(clientSocket));
                    // thread.start();
                }
            }
        }
    }

    private static void trataRequisicao(Socket clientSocket) {
        try (clientSocket){ // try-with-resources para garantir que o socket seja fechado após o tratamento da requisição
            InputStream clientIS = clientSocket.getInputStream();

            StringBuilder requestBuilder = new StringBuilder();
            int data;
            do {
                data = clientIS.read();
                requestBuilder.append((char) data);
            } while (clientIS.available() > 0);

            String request = requestBuilder.toString();
            System.out.println("------------------------------");
            System.out.println(request); // out é um PrintStream
            System.out.println("\n\nChegou um novo request");

            Thread.sleep(250); // simula um processamento mais demorado
            // RFC 2616: https://www.ietf.org/rfc/rfc2616.txt
            // seção 5 Request
            String[] requestChunks = request.split("\r\n\r\n"); // \r = carriage return, \n = new line
            String requestLineAndHeaders = requestChunks[0];
            String[] requestLineAndHeadersChunks = requestLineAndHeaders.split("\r\n");
            String requestLine = requestLineAndHeadersChunks[0]; // 5.1 Request-Line
            String[] requestLineChunks = requestLine.split(" ");

            // method
            String method = requestLineChunks[0];
            // uri
            String requestURI = requestLineChunks[1];

            System.out.println("Method: " + method);
            System.out.println("URI: " + requestURI);

            OutputStream clientOS = clientSocket.getOutputStream();
            PrintStream clientOut = new PrintStream(clientOS); // crio o PrintStream para enviar para o out
            if (method.equals("GET") && requestURI.equals("/itens-cardapio")) {
                System.out.println("GET /itens-cardapio");

                Path path = Path.of("itensCardapio.json");
                String json = Files.readString(path);

                clientOut.println("HTTP/1.1 200 OK");
                clientOut.println("Content-Type: application/json; charset=UTF-8");
                clientOut.println("Content-Length: " + json.getBytes().length);
                clientOut.println(); // linha em branco para separar os headers do body
                clientOut.println(json);
            } else if (method.equals("GET") && requestURI.equals("/itens-cardapio/total")) {
                System.out.println("GET /itens-cardapio/total");
                List<ItemCardapio> listaDeItensCardapio = database.listaDeItensCardapio();

                clientOut.println("HTTP/1.1 200 OK");
                clientOut.println("Content-Type: application/json; charset=UTF-8");
                clientOut.println();
                clientOut.println(listaDeItensCardapio.size());
            } else if (method.equals("POST") && requestURI.equals("/itens-cardapio")) {
                System.out.println("POST /itens-cardapio");
                if (requestChunks.length == 1) {
                    clientOut.println("HTTP/1.1 400 Bad Request");
                    return;
                }
                String body = requestChunks[1];

                Gson gson = new Gson();
                ItemCardapio novoItemCardapio = gson.fromJson(body, ItemCardapio.class);

                database.adicionaItemCardapio(novoItemCardapio);
                clientOut.println("HTTP/1.1 201 Created");
//                clientOut.println("Content-Type: application/json; charset=UTF-8");
            } else {
                System.out.println("GET para URI desconhecida: " + requestURI);
                clientOut.println("HTTP/1.1 404 Not Found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
