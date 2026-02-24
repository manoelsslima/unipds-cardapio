package mx.florinda.cardapio;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ClienteViaCEP {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://viacep.com.br/ws/01001000/json");
        // abre a conexão
        URLConnection urlConnection = url.openConnection();

        // pega a inputstream
        Scanner scanner = new Scanner(urlConnection.getInputStream());
        while(scanner.hasNext()) {
            System.out.println(scanner.nextLine());
        }
    }
}
