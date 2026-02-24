package mx.florinda.cardapio;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServidorItensCardapio {
    public static void main(String[] args) throws IOException {


        InetSocketAddress inetSocketAdress = new InetSocketAddress(8099);
        HttpServer server = HttpServer.create(inetSocketAdress, 0);

        server.createContext("/itens-cardapio", exchange -> {
            Path path = Path.of("itensCardapio.json");
            String json = Files.readString(path);
            byte[] bytes = json.getBytes();

            Headers headers = exchange.getResponseHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");

            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(bytes);
        });
        System.out.println("Subiu servidor http!");
        server.start();
    }
}
