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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorItensCardapioComSocket {

    /*
    Níveis de debug:
    FINEST: rastreamento muito detalhado, usado para depuração bem granular
    FINER: rastreamento detalhado, mas um pouco menos verboso que o FINEST
    FINE: informações de rastreio gerais (debug/tracing)
    CONFIG: mensagens de configuração estática (parâmetros, inicialização, etc.)
    INFO: mensagens informativas, como início de processamento, chegada de requisições, etc.
    WARNING: mensagens de advertência, indicando situações inesperadas ou potenciais problemas
    SEVERE: mensagens de erro grave, indicando falhas que podem comprometer o funcionamento do sistema
     */
    private static final Logger logger = Logger.getLogger(ServidorItensCardapioComSocket.class.getName());

    private static final Database database = new SQLDatabase();

    public static void main(String[] args) throws IOException {

        // limitando em 50 o número de threads. Sugestão: no máximo o número de cpus
        try (ExecutorService executor = Executors.newFixedThreadPool(50)) {

            try (ServerSocket serverSocket = new ServerSocket(8099)) {
                logger.info("Servidor de itens do cardapio rodando na porta 8099...");

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
        try (clientSocket) { // try-with-resources para garantir que o socket seja fechado após o tratamento da requisição
            InputStream clientIS = clientSocket.getInputStream();

            StringBuilder requestBuilder = new StringBuilder();
            int data;
            do {
                data = clientIS.read();
                requestBuilder.append((char) data);
            } while (clientIS.available() > 0);

            String request = requestBuilder.toString();
            logger.finer("------------------------------");
            logger.finest(request); // out é um PrintStream
            logger.fine("\n\nChegou um novo request");

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
            // http version
            String httpVersion = requestLineChunks[2];

            // usando supplier<string>, só consome a memória se o finer estiver habilitado
            logger.finer(() -> "Method: " + method);
            logger.finer(() -> "URI: " + requestURI);
            logger.finer(() -> "HTTP Version: " + httpVersion);

            OutputStream clientOS = clientSocket.getOutputStream();
            PrintStream clientOut = new PrintStream(clientOS); // crio o PrintStream para enviar para o out

            try {
                if (method.equals("GET") && requestURI.equals("/itens-cardapio/json")) {
                    logger.fine("GET /itens-cardapio/json");

                    Path path = Path.of("itensCardapio.json");
                    String json = Files.readString(path);

                    clientOut.println("HTTP/1.1 200 OK");
                    clientOut.println("Content-Type: application/json; charset=UTF-8");
                    clientOut.println("Content-Length: " + json.getBytes().length);
                    clientOut.println(); // linha em branco para separar os headers do body
                    clientOut.println(json);
                } else if (method.equals("GET") && requestURI.equals("/itens-cardapio")) {
                    logger.fine("GET /itens-cardapio");

                    List<ItemCardapio> listaDeItensCardapio = database.listaDeItensCardapio();
                    Gson gson = new Gson();
                    String json = gson.toJson(listaDeItensCardapio);

                    clientOut.println("HTTP/1.1 200 OK");
                    clientOut.println("Content-Type: application/json; charset=UTF-8");
                    clientOut.println("Content-Length: " + json.getBytes().length);
                    clientOut.println(); // linha em branco para separar os headers do body
                    clientOut.println(json);
                } else if (method.equals("GET") && requestURI.equals("/itens-cardapio/total")) {
                    logger.fine("GET /itens-cardapio/total");
                    // List<ItemCardapio> listaDeItensCardapio = database.listaDeItensCardapio();

                    clientOut.println("HTTP/1.1 200 OK");
                    clientOut.println("Content-Type: application/json; charset=UTF-8");
                    clientOut.println();
                    // clientOut.println(listaDeItensCardapio.size());
                    clientOut.println(database.totalItensCardapio());
                } else if (method.equals("POST") && requestURI.equals("/itens-cardapio")) {
                    logger.fine("POST /itens-cardapio");
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
                    logger.warning(() -> "GET para URI desconhecida: " + requestURI);
                    clientOut.println("HTTP/1.1 404 Not Found");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, e, () -> "Erro ao tratar " + method + " " + requestURI);
                clientOut.println("HTTP/1.1 500 Internal Server Error");
                clientOut.println();
                clientOut.println(e.getMessage());
            }
        } catch (Exception e) {
            // logger.severe("Erro no servidor");
            logger.log(Level.SEVERE, "Erro no servidor", e);
            throw new RuntimeException(e);
        }
    }
}
