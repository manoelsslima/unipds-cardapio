package mx.florinda.cardapio;

import java.net.URL;
import java.util.Scanner;

public class ClienteViaCEP {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://viacep.com.br/ws/01001000/json");

        Scanner scanner = new Scanner(url.openStream());
        while(scanner.hasNext()) {
            System.out.println(scanner.nextLine());
        }
        scanner.close();
    }
}
