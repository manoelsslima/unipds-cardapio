package mx.florinda.cardapio;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteViaCEP {

    public static void main(String[] args) throws Exception {

        // java 1.0
        // URL url = new URL("https://viacep.com.br/ws/01001000/json");

//        try (Scanner scanner = new Scanner(url.openStream())) {
//            while (scanner.hasNext()) {
//                System.out.println(scanner.nextLine());
//            }
//        }

        // java 1.4
        URI uri = URI.create("https://viacep.com.br/ws/01001000/json");
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest httpRequest = HttpRequest.newBuilder(uri).build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = httpResponse.statusCode();
            String body = httpResponse.body();
            System.out.println("Status code: " + statusCode);
            System.out.println("Body: " + body);
        }
    }
}
